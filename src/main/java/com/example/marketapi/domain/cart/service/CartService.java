package com.example.marketapi.domain.cart.service;

import com.example.marketapi.domain.cart.repository.CartInfoRepository;
import com.example.marketapi.domain.cart.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartInfoRepository cartInfoRepository;
    private final CartItemRepository cartItemRepository;

    public void createUserCart(Long userId) {
    }
    public void getUserCartItems(Long userId) {
    }
    public void addProductToCart(Long cartId, Long productId) {
    }

}
