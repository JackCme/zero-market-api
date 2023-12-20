package com.example.marketapi.domain.order.dto;

import com.example.marketapi.domain.order.entity.OrderInfo;
import com.example.marketapi.domain.order.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfoDto {
    private Long orderId;
    private List<OrderItemDto> orderItems;

    public static OrderInfoDto fromEntity(OrderInfo orderInfo, List<OrderItem> orderItems) {
        return OrderInfoDto.builder()
                .orderId(orderInfo.getOrderId())
                .orderItems(orderItems.stream()
                        .map(OrderItemDto::fromEntity)
                        .collect(Collectors.toList()))
                .build();
    }
}
