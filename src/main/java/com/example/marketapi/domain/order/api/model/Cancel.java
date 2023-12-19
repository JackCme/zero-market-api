package com.example.marketapi.domain.order.api.model;

import com.example.marketapi.domain.order.dto.OrderInfoDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class Cancel {
    @Getter
    @AllArgsConstructor
    public static class Request {
        private Long orderId;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private OrderInfoDto orderInfoDto;
    }
}
