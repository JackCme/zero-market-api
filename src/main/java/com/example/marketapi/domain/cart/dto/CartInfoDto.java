package com.example.marketapi.domain.cart.dto;

import com.example.marketapi.domain.cart.entity.CartInfo;
import com.example.marketapi.domain.cart.entity.CartItem;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartInfoDto {
    private Long cartId;
    private Long cartItemCount;
    @Builder.Default
    private List<CartItemDto> cartItemList = new ArrayList<>();

    public static CartInfoDto fromEntity(CartInfo cartInfo) {
        return CartInfoDto.builder()
                .cartId(cartInfo.getCartId())
                .cartItemCount(cartInfo.getItemCount())
                .cartItemList(
                        cartInfo.getCartItemList().stream()
                                .map(CartItemDto::fromEntity)
                                .collect(Collectors.toList())
                )
                .build();
    }

}
