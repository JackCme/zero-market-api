package com.example.marketapi.domain.cart.entity;

import com.example.marketapi.domain.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Long productCnt;

    @ManyToOne
    @JoinColumn(name="cart_id")
    private CartInfo cartInfo;
    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;
}
