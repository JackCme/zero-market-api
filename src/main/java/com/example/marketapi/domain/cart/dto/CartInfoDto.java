package com.example.marketapi.domain.cart.dto;

import com.example.marketapi.domain.cart.entity.CartInfo;
import com.example.marketapi.domain.cart.entity.CartItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartInfoDto {
    private Long cartId;
    private Long cartItemCount;
    private List<CartItem> cartItemList = new ArrayList<>();

    public static CartInfoDto fromEntity(CartInfo cartInfo) {
        return CartInfoDto.builder()
                .cartId(cartInfo.getCartId())
                .cartItemCount(cartInfo.getItemCount())
                .cartItemList(cartInfo.getCartItemList())
                .build();
    }

}
