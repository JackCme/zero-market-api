package com.example.marketapi.domain.order.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderItemStatus {
    ORDERED("ORDERED")
    ,CANCELED("CANCELED")
    ;
    private final String value;

}
