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
    private Long id;
    @Builder.Default
    private Long productCnt = 0L;

    @ManyToOne
    @JoinColumn(name="cart_id")
    private CartInfo cartInfo;
    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    public static CartItem createNewCartItem(CartInfo cartInfo, Product product) {
        return CartItem.builder()
                .cartInfo(cartInfo)
                .product(product)
                .build();
    }

    public CartItem addCount(Long count) {
        this.productCnt += count;
        return this;
    }
}
