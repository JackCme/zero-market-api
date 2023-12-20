package com.example.marketapi.domain.cart.service;

import com.example.marketapi.domain.cart.dto.CartInfoDto;
import com.example.marketapi.domain.cart.entity.CartInfo;
import com.example.marketapi.domain.cart.entity.CartItem;
import com.example.marketapi.domain.cart.repository.CartInfoRepository;
import com.example.marketapi.domain.cart.repository.CartItemRepository;
import com.example.marketapi.domain.product.dto.ProductDto;
import com.example.marketapi.domain.product.entity.Product;
import com.example.marketapi.domain.product.service.ProductService;
import com.example.marketapi.domain.user.entity.UserAccount;
import com.example.marketapi.global.exception.GlobalException;
import com.example.marketapi.global.exception.model.ResultCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartInfoRepository cartInfoRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductService productService;

    @Transactional
    public void createUserCart(Long userId) {
        cartInfoRepository.findCartInfoByUserAccountUserId(userId)
                .ifPresent(cart -> {
                    throw new GlobalException(ResultCode.CART_INFO_ALREADY_EXISTS);
                });
        cartInfoRepository.save(CartInfo.builder()
                        .itemCount(0L)
                        .userAccount(UserAccount.builder().userId(userId).build())
                .build());
    }

    @Transactional
    public CartInfoDto getUserCartInfo(Long userId) {
        CartInfo cartInfo = cartInfoRepository.findCartInfoByUserAccountUserId(userId)
                .orElseThrow(() -> new GlobalException(ResultCode.CART_INFO_NOT_EXISTS));
        return CartInfoDto.fromEntity(cartInfo);
    }
    @Transactional
    public List<CartItem> getUserCartItems(Long userId) {
        CartInfo cartInfo = cartInfoRepository.findCartInfoByUserAccountUserId(userId)
                .orElseThrow(() -> new GlobalException(ResultCode.CART_INFO_NOT_EXISTS));
        return cartInfo.getCartItemList();
    }

    @Transactional
    public CartInfoDto addProductToCart(Long cartId, Long productId, Long count) {
        Product product = ProductDto.toEntity(productService.getProduct(productId));
        CartInfo cartInfo = findCartInfoByCartId(cartId);
        CartItem cartItem = cartInfo.getCartItemList().stream()
                .filter(item -> Objects.equals(item.getProduct().getProductId(), product.getProductId()))
                .findFirst()
                .orElse(CartItem.createNewCartItem(cartInfo, product))
                .addCount(count);

        checkProductStockAvailable(product, cartItem);

        cartItemRepository.save(cartItem);

        List<CartItem> cartItemList = cartItemRepository.findCartItemsByCartInfo(cartInfo);
        CartInfo newCartInfo = CartInfo.builder()
                .cartId(cartInfo.getCartId())
                .userAccount(cartInfo.getUserAccount())
                .itemCount((long) cartItemList.size())
                .cartItemList(cartItemList)
                .build();
        CartInfo saved = cartInfoRepository.save(newCartInfo);
        return CartInfoDto.fromEntity(saved);
    }

    private CartInfo findCartInfoByCartId(Long cartId) {
        return cartInfoRepository.findById(cartId)
                .orElseThrow(() -> new GlobalException(ResultCode.CART_INFO_NOT_EXISTS));
    }

    private void checkProductStockAvailable(Product product, CartItem cartItem) {
        if (product.getInStock() < cartItem.getProductCnt()) {
            throw new GlobalException(ResultCode.INSUFFICIENT_STOCK_EXCEPTION);
        }
    }

}
