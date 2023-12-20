package com.example.marketapi.domain.cart.dto;

import com.example.marketapi.domain.cart.entity.CartItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {
    private Long productId;
    private Long productCount;
    private String productName;
    private Long price;

    public static CartItemDto fromEntity(CartItem cartItem) {
        return CartItemDto.builder()
                .productId(cartItem.getProduct().getProductId())
                .productCount(cartItem.getProductCnt())
                .productName(cartItem.getProduct().getName())
                .price(cartItem.getProduct().getPrice())
                .build();
    }
}
