package com.example.marketapi.domain.cart.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class CartAddProduct {
    @Getter
    @AllArgsConstructor
    public static class Request {
        private Long productId;
        private Long count;
    }
}
