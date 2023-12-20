package com.example.marketapi.domain.cart.repository;

import com.example.marketapi.domain.cart.entity.CartInfo;
import com.example.marketapi.domain.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findCartItemsByCartInfo(CartInfo cartInfo);
}
