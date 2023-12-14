package com.example.marketapi.domain.cart.repository;

import com.example.marketapi.domain.cart.entity.CartItem;
import com.example.marketapi.domain.cart.entity.CartItemID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, CartItemID> {
}
