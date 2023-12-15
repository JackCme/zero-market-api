package com.example.marketapi.domain.cart.service;

import com.example.marketapi.domain.cart.entity.CartItem;
import com.example.marketapi.domain.cart.repository.CartInfoRepository;
import com.example.marketapi.domain.cart.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartInfoRepository cartInfoRepository;
    private final CartItemRepository cartItemRepository;

    @Transactional
    public void createUserCart(Long userId) {
    }
    @Transactional
    public ArrayList<CartItem> getUserCartItems(Long userId) {
        return null;
    }
    @Transactional
    public void addProductToCart(Long cartId, Long productId, Long count) {
    }

}
