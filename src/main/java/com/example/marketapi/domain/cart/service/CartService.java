package com.example.marketapi.domain.cart.service;

import com.example.marketapi.domain.cart.entity.CartInfo;
import com.example.marketapi.domain.cart.entity.CartItem;
import com.example.marketapi.domain.cart.entity.CartItemID;
import com.example.marketapi.domain.cart.exception.CartException;
import com.example.marketapi.domain.cart.repository.CartInfoRepository;
import com.example.marketapi.domain.cart.repository.CartItemRepository;
import com.example.marketapi.domain.product.dto.ProductDto;
import com.example.marketapi.domain.product.entity.Product;
import com.example.marketapi.domain.product.exception.ProductException;
import com.example.marketapi.domain.product.service.ProductService;
import com.example.marketapi.domain.user.entity.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
                    throw new CartException(CartException.ErrorCode.CART_INFO_ALREADY_EXISTS);
                });
        cartInfoRepository.save(CartInfo.builder()
                        .itemCount(0L)
                        .userAccount(UserAccount.builder().userId(userId).build())
                .build());
    }

    @Transactional
    public List<CartItem> getUserCartItems(Long userId) {
        CartInfo cartInfo = cartInfoRepository.findCartInfoByUserAccountUserId(userId)
                .orElseThrow(() -> new CartException(CartException.ErrorCode.CART_INFO_NOT_EXISTS));
        return cartInfo.getCartItemList();
    }

    @Transactional
    public void addProductToCart(Long cartId, Long productId, Long count) {
        Product product = ProductDto.toEntity(productService.getProduct(productId));
        if (product.getInStock() < count) {
            throw new ProductException(ProductException.ErrorCode.INSUFFICIENT_STOCK_EXCEPTION);
        }
        CartInfo cartInfo = cartInfoRepository.findById(cartId)
                .orElseThrow(() -> new CartException(CartException.ErrorCode.CART_INFO_NOT_EXISTS));

        CartItem cartItem = CartItem.builder()
                .cartInfo(cartInfo)
                .productCnt(count)
                .product(product)
                .build();
        cartItemRepository.save(cartItem);
        CartInfo newCartInfo = CartInfo.builder()
                .cartId(cartInfo.getCartId())
                .itemCount(cartInfo.getItemCount() + 1)
                .cartItemList(cartInfo.getCartItemList())
                .userAccount(cartInfo.getUserAccount())
                .build();
        cartInfoRepository.save(newCartInfo);
    }

}
