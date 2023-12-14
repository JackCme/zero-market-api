package com.example.marketapi.domain.cart.entity;

import com.example.marketapi.domain.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CartItem {
    @EmbeddedId
    private CartItemID cartItemID;

    private Long userId;
    private Long productCnt;

    @ManyToOne
    @MapsId("cartId")
    private CartInfo cartInfo;
    @ManyToOne
    @MapsId("productId")
    private Product product;
}
