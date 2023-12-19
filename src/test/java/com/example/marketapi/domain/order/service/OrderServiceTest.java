package com.example.marketapi.domain.order.service;

import com.example.marketapi.domain.cart.entity.CartInfo;
import com.example.marketapi.domain.cart.entity.CartItem;
import com.example.marketapi.domain.cart.repository.CartInfoRepository;
import com.example.marketapi.domain.order.dto.OrderInfoDto;
import com.example.marketapi.domain.order.entity.OrderInfo;
import com.example.marketapi.domain.order.entity.OrderItem;
import com.example.marketapi.domain.order.entity.OrderItemStatus;
import com.example.marketapi.domain.order.repository.OrderInfoRepository;
import com.example.marketapi.domain.order.repository.OrderItemRepository;
import com.example.marketapi.domain.product.entity.Product;
import com.example.marketapi.domain.product.service.ProductService;
import com.example.marketapi.domain.user.entity.UserAccount;
import com.example.marketapi.global.exception.GlobalException;
import com.example.marketapi.global.exception.model.ResultCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    private OrderInfoRepository orderInfoRepository;
    @Mock
    private OrderItemRepository orderItemRepository;
    @Mock
    private CartInfoRepository cartInfoRepository;
    @Mock
    private ProductService productService;
    @InjectMocks
    private OrderService orderService;


    @Test
    @DisplayName("주문 생성 성공")
    void orderCheckoutSuccess() {
        // Given
        Long cartId = 1L;
        Long userId = 1L;
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(CartItem.builder()
                .product(Product.builder()
                        .productId(1L)
                        .name("재고있음1")
                        .inStock(100L)
                        .build())
                .productCnt(10L)
                .build());
        cartItems.add(CartItem.builder()
                .product(Product.builder()
                        .productId(2L)
                        .name("재고있음")
                        .inStock(100L)
                        .build())
                .productCnt(10L)
                .build());
        CartInfo cartInfo = CartInfo.builder()
                .cartId(cartId)
                .cartItemList(cartItems)
                .build();
        given(cartInfoRepository.findCartInfoByUserAccountUserId(anyLong())).willReturn(Optional.of(cartInfo));
        // When
        OrderInfoDto orderInfoDto = orderService.checkoutOrderFromCart(cartId, userId);
        // Then
        verify(productService, times(cartItems.size())).decreaseProductStock(anyLong(), anyLong());
        assertEquals(orderInfoDto.getOrderItems().size(), cartItems.size());
        assertEquals(OrderItemStatus.ORDERED, orderInfoDto.getOrderItems().get(0).getOrderItemStatus());
        assertEquals(OrderItemStatus.ORDERED, orderInfoDto.getOrderItems().get(1).getOrderItemStatus());
    }

    @Test
    @DisplayName("주문 생성 실패 - 상품 재고 없음")
    void orderCheckoutFailed_InsufficientStock() {
        // Given
        Long cartId = 1L;
        Long userId = 1L;
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(CartItem.builder()
                .product(Product.builder()
                        .name("재고없음")
                        .inStock(0L)
                        .build())
                .productCnt(10L)
                .build());
        cartItems.add(CartItem.builder()
                .product(Product.builder()
                        .name("재고있음")
                        .inStock(100L)
                        .build())
                .productCnt(10L)
                .build());
        CartInfo cartInfo = CartInfo.builder()
                .cartId(cartId)
                .cartItemList(cartItems)
                .build();
        given(cartInfoRepository.findCartInfoByUserAccountUserId(anyLong())).willReturn(Optional.of(cartInfo));
        // When
        GlobalException globalException = assertThrows(GlobalException.class, () -> orderService.checkoutOrderFromCart(cartId, userId));
        // Then
        assertEquals(ResultCode.INSUFFICIENT_STOCK_EXCEPTION, globalException.getResultCode());
        assertEquals(ResultCode.INSUFFICIENT_STOCK_EXCEPTION.getDescription(), globalException.getResultCode().getDescription());

    }

    @Test
    @DisplayName("주문 생성 실패 - 장바구니 소유자와 요청자가 다름")
    void orderCheckoutFailed_ForbiddenRequest() {
        // Given
        Long cartId = 1L;
        Long userId = 1L;
        CartInfo cartInfo = CartInfo.builder()
                .cartId(cartId)
                .userAccount(UserAccount.builder()
                        .userId(2L)
                        .build())
                .build();
        given(cartInfoRepository.findCartInfoByUserAccountUserId(anyLong())).willReturn(Optional.of(cartInfo));
        // When
        GlobalException globalException = assertThrows(GlobalException.class, () -> orderService.checkoutOrderFromCart(cartId, userId));
        // Then
        assertEquals(ResultCode.ACCESS_DENIED, globalException.getResultCode());
        assertEquals(ResultCode.ACCESS_DENIED.getDescription(), globalException.getResultCode().getDescription());
    }

    @Test
    @DisplayName("주문 생성 실패 - 장바구니 상품 없음")
    void orderCheckoutFailed_NoProductInCart() {
        // Given
        Long cartId = 1L;
        Long userId = 1L;
        CartInfo cartInfo = CartInfo.builder()
                .build();
        given(cartInfoRepository.findCartInfoByUserAccountUserId(anyLong())).willReturn(Optional.of(cartInfo));
        // When
        GlobalException globalException = assertThrows(GlobalException.class, () -> orderService.checkoutOrderFromCart(cartId, userId));
        // Then
        assertEquals(ResultCode.CART_ITEM_EMPTY, globalException.getResultCode());
        assertEquals(ResultCode.CART_ITEM_EMPTY.getDescription(), globalException.getResultCode().getDescription());
    }

    @Test
    @DisplayName("주문 취소 성공")
    void orderCancelSuccess() {
        // Given
        Long productId = 1L;
        Long orderId = 1L;
        Long userId = 1L;
        ArrayList<OrderItem> orderItems = new ArrayList<>();
        OrderItem orderItem = OrderItem.builder()
                .product(Product.builder()
                        .productId(productId)
                        .build())
                .status(OrderItemStatus.ORDERED)
                .count(2L)
                .build();
        orderItems.add(orderItem);
        OrderInfo orderInfo = OrderInfo.builder()
                .orderId(orderId)
                .orderItemList(orderItems)
                .userAccount(UserAccount.builder().userId(userId).build())
                .build();
        given(orderInfoRepository.findById(anyLong())).willReturn(Optional.of(orderInfo));
        // When
        OrderInfoDto orderInfoDto = orderService.cancelOrderProduct(orderId, productId, userId);
        // Then
        verify(productService, times(1)).increaseProductStock(productId, orderItem.getCount());
        assertEquals(OrderItemStatus.CANCELED, orderInfoDto.getOrderItems().get(0).getOrderItemStatus());
    }

    @Test
    @DisplayName("주문 취소 실패 - 주문 소유자와 요청자가 다름")
    void orderCancelFailed_ForbiddenRequest() {
        // Given
        Long productId = 1L;
        Long orderId = 1L;
        ArrayList<OrderItem> orderItems = new ArrayList<>();
        OrderItem orderItem = OrderItem.builder()
                .product(Product.builder()
                        .productId(productId)
                        .build())
                .status(OrderItemStatus.ORDERED)
                .count(2L)
                .build();
        orderItems.add(orderItem);
        OrderInfo orderInfo = OrderInfo.builder()
                .orderId(orderId)
                .userAccount(UserAccount.builder()
                        .userId(2L)
                        .build())
                .orderItemList(orderItems)
                .build();
        given(orderInfoRepository.findById(anyLong())).willReturn(Optional.of(orderInfo));
        // When
        GlobalException globalException = assertThrows(GlobalException.class, () -> orderService.cancelOrderProduct(anyLong(), anyLong(), 1L));
        // Then
        assertEquals(ResultCode.ACCESS_DENIED, globalException.getResultCode());
        assertEquals(ResultCode.ACCESS_DENIED.getDescription(), globalException.getResultCode().getDescription());
    }

    @Test
    @DisplayName("주문 취소 싪패 - 주문이 존재하지 않음")
    void orderCancelFailed_OrderDoesNotExists() {
        // Given
        given(orderInfoRepository.findById(anyLong())).willReturn(Optional.empty());
        // When
        GlobalException globalException = assertThrows(GlobalException.class, () -> orderService.cancelOrderProduct(anyLong(), anyLong(), anyLong()));
        // Then
        assertEquals(ResultCode.ORDER_INFO_NOT_EXISTS, globalException.getResultCode());
        assertEquals(ResultCode.ORDER_INFO_NOT_EXISTS.getDescription(), globalException.getResultCode().getDescription());
    }

    @Test
    @DisplayName("주문 취소 실패 - 이미 취소된 주문")
    void orderCancelFailed_OrderAlreadyCanceled() {
        // Given
        ArrayList<OrderItem> orderItems = new ArrayList<>();
        OrderItem orderItem = OrderItem.builder()
                .status(OrderItemStatus.CANCELED)
                .build();
        orderItems.add(orderItem);
        OrderInfo orderInfo = OrderInfo.builder()
                .orderItemList(orderItems)
                .build();
        given(orderInfoRepository.findById(anyLong())).willReturn(Optional.of(orderInfo));
        // When
        GlobalException globalException = assertThrows(GlobalException.class, () -> orderService.cancelOrderProduct(anyLong(), anyLong(), anyLong()));
        // Then
        assertEquals(ResultCode.ORDER_ITEM_ALREADY_CANCELED, globalException.getResultCode());
        assertEquals(ResultCode.ORDER_ITEM_ALREADY_CANCELED.getDescription(), globalException.getResultCode().getDescription());
    }

    @Test
    @DisplayName("주문 취소 실패 - 취소할 주문 상품이 존재하지 않음")
    void orderCancelFailed_OrderItemDoesNotExists() {
        // Given
        Long productId = 1L;
        ArrayList<OrderItem> orderItems = new ArrayList<>();
        OrderItem orderItem = OrderItem.builder()
                .product(Product.builder()
                        .productId(productId)
                        .build())
                .status(OrderItemStatus.ORDERED)
                .build();
        orderItems.add(orderItem);
        OrderInfo orderInfo = OrderInfo.builder()
                .orderItemList(orderItems)
                .build();
        given(orderInfoRepository.findById(anyLong())).willReturn(Optional.of(orderInfo));
        // When
        GlobalException globalException = assertThrows(GlobalException.class, () -> orderService.cancelOrderProduct(anyLong(), 2L, anyLong()));
        // Then
        assertEquals(ResultCode.ORDER_ITEM_ALREADY_CANCELED, globalException.getResultCode());
        assertEquals(ResultCode.ORDER_ITEM_ALREADY_CANCELED.getDescription(), globalException.getResultCode().getDescription());
    }

    // TODO: 부분취소 구현 했을 때 테스트 추가할 것
//    @Test
//    @DisplayName("주문 취소 실패 - 취소할 주문 상품의 갯수가 주문 상품 갯수를 초과함")
//    void orderCancelFailed_CancelCountExceedOrderCount() {
//        // Given
//        Long productId = 1L;
//        ArrayList<OrderItem> orderItems = new ArrayList<>();
//        OrderItem orderItem = OrderItem.builder()
//                .product(Product.builder()
//                        .productId(productId)
//                        .build())
//                .status(OrderItemStatus.ORDERED)
//                .count(1L)
//                .build();
//        orderItems.add(orderItem);
//        OrderInfo orderInfo = OrderInfo.builder()
//                .orderItemList(orderItems)
//                .build();
//        given(orderInfoRepository.findById(anyLong())).willReturn(Optional.of(orderInfo));
//        // When
//        GlobalException globalException = assertThrows(GlobalException.class, () -> orderService.cancelOrderProduct(anyLong(), anyLong()));
//        // Then
//        assertEquals(ResultCode.CANCEL_COUNT_EXCEED_ORDER_COUNT, globalException.getResultCode());
//        assertEquals(ResultCode.CANCEL_COUNT_EXCEED_ORDER_COUNT.getDescription(), globalException.getResultCode().getDescription());
//    }


}
