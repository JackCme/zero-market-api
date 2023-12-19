package com.example.marketapi.domain.order.api.model;

import com.example.marketapi.domain.order.dto.OrderInfoDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class Checkout {
    @Getter
    @AllArgsConstructor
    public static class Request {
        private Long cartId;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private OrderInfoDto orderInfoDto;
    }
}
