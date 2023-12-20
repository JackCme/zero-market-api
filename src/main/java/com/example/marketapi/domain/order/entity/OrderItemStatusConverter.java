package com.example.marketapi.domain.order.entity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;

@Converter
public class OrderItemStatusConverter implements AttributeConverter<OrderItemStatus, String> {

    @Override
    public String convertToDatabaseColumn(OrderItemStatus attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public OrderItemStatus convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return Arrays.stream(OrderItemStatus.values())
                .filter(v -> v.getValue().equals(dbData))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("주문상태 %s가 존재하지 않습니다.", dbData)));
    }
}
