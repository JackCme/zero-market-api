package com.example.marketapi.domain.cart.api.model;

import com.example.marketapi.domain.cart.entity.CartItem;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

public class GetCartInfo {
    @Getter
    @AllArgsConstructor
    public static class Response {
        private Long cartId;
        private Long cartItemCount;
        private List<CartItem> cartItems;

        // TODO: from converter helper method 작성
    }
}
