package com.example.marketapi.domain.order.service;

import com.example.marketapi.domain.cart.entity.CartInfo;
import com.example.marketapi.domain.cart.repository.CartInfoRepository;
import com.example.marketapi.domain.order.dto.OrderInfoDto;
import com.example.marketapi.domain.order.entity.OrderInfo;
import com.example.marketapi.domain.order.entity.OrderItem;
import com.example.marketapi.domain.order.entity.OrderItemStatus;
import com.example.marketapi.domain.order.repository.OrderInfoRepository;
import com.example.marketapi.domain.order.repository.OrderItemRepository;
import com.example.marketapi.domain.product.service.ProductService;
import com.example.marketapi.domain.user.entity.UserAccount;
import com.example.marketapi.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.example.marketapi.domain.order.entity.OrderItemStatus.CANCELED;
import static com.example.marketapi.domain.order.entity.OrderItemStatus.ORDERED;
import static com.example.marketapi.global.exception.model.ResultCode.*;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderInfoRepository orderInfoRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartInfoRepository cartInfoRepository;
    private final ProductService productService;

    @Transactional
    public OrderInfoDto checkoutOrderFromCart(Long cartId, Long userId) {
        CartInfo cartInfo = cartInfoRepository.findById(cartId)
                .orElseThrow(() -> new GlobalException(CART_INFO_NOT_EXISTS));
        validateCartInfo(cartInfo, userId);

        OrderInfo orderInfo = orderInfoRepository.save(OrderInfo.builder()
                .userAccount(UserAccount.builder().userId(userId).build())
                .build());
        List<OrderItem> orderItems = createOrderItemsFromCart(cartInfo, orderInfo);
        orderItemRepository.saveAll(orderItems);
        orderItems.forEach(item ->
                productService.decreaseProductStock(item.getProduct().getProductId(), item.getCount()));
        return OrderInfoDto.fromEntity(orderInfo, orderItems);
    }

    private List<OrderItem> createOrderItemsFromCart(CartInfo cartInfo, OrderInfo orderInfo) {
        return cartInfo.getCartItemList()
                .stream()
                .map(cartItem -> OrderItem.builder()
                        .status(ORDERED)
                        .orderInfo(orderInfo)
                        .count(cartItem.getProductCnt())
                        .product(cartItem.getProduct())
                        .build())
                .collect(Collectors.toList());
    }

    private void validateCartInfo(CartInfo cartInfo, Long userId) {
        if (!Objects.equals(cartInfo.getUserAccount().getUserId(), userId)) {
            throw new GlobalException(ACCESS_DENIED);
        }
        if (cartInfo.getCartItemList().isEmpty()) {
            throw new GlobalException(CART_ITEM_EMPTY);
        }
        cartInfo.getCartItemList().forEach(item -> {
            if (item.getProduct().getInStock() < item.getProductCnt()) {
                throw new GlobalException(INSUFFICIENT_STOCK_EXCEPTION);
            }
        });
    }

    public OrderInfoDto cancelOrderProduct(Long orderId, Long productId, Long userId) {
        OrderInfo orderInfo = orderInfoRepository.findById(orderId).orElseThrow(() -> new GlobalException(ORDER_INFO_NOT_EXISTS));
        OrderItem orderItem = orderInfo.getOrderItemList().stream()
                .filter(item -> Objects.equals(item.getProduct().getProductId(), productId))
                .findFirst()
                .orElseThrow(() -> new GlobalException(CANCEL_ORDER_ITEM_NOT_EXISTS));
        validateCancelOrder(orderInfo, orderItem, userId);

        orderItem.setStatus(CANCELED);
        orderItemRepository.save(orderItem);

        productService.increaseProductStock(orderItem.getProduct().getProductId(), orderItem.getCount());

        return OrderInfoDto.fromEntity(orderInfo, orderInfo.getOrderItemList());
    }

    private void validateCancelOrder(OrderInfo orderInfo, OrderItem orderItem, Long userId) {
        if (!Objects.equals(orderInfo.getUserAccount().getUserId(), userId)) {
            throw new GlobalException(ACCESS_DENIED);
        }
        if (orderItem.getStatus().equals(OrderItemStatus.CANCELED)) {
            throw new GlobalException(CANCEL_ORDER_ITEM_ALREADY_CANCELED);
        }
    }
}
