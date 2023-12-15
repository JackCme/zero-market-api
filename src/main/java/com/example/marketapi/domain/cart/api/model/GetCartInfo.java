package com.example.marketapi.domain.cart.api.model;

import com.example.marketapi.domain.cart.dto.CartInfoDto;
import com.example.marketapi.domain.product.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class GetCartInfo {
    @Getter
    @Builder
    @AllArgsConstructor
    public static class Response {
        private Long cartId;
        private Long cartItemCount;
        private List<ProductDto> cartItems;

        public static Response from(CartInfoDto cartInfoDto) {
                return Response.builder()
                        .cartId(cartInfoDto.getCartId())
                        .cartItemCount(cartInfoDto.getCartItemCount())
                        .cartItems(
                                cartInfoDto.getCartItemList().stream()
                                        .map(cartItem -> ProductDto.fromEntity(cartItem.getProduct()))
                                        .collect(Collectors.toList())
                        )
                        .build();
        }
    }
}
