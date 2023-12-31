package com.example.marketapi.domain.order.dto;

import com.example.marketapi.domain.order.entity.OrderItem;
import com.example.marketapi.domain.order.entity.OrderItemStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemDto {
    private Long productId;
    private String name;
    private Long orderCount;
    private OrderItemStatus orderItemStatus;

    public static OrderItemDto fromEntity(OrderItem orderItem) {
        return OrderItemDto.builder()
                .productId(orderItem.getProduct().getProductId())
                .name(orderItem.getProduct().getName())
                .orderCount(orderItem.getCount())
                .orderItemStatus(orderItem.getStatus())
                .build();
    }
}
