package com.example.marketapi.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfoDto {
    private Long orderId;
    private List<OrderItemDto> orderItems;
}
