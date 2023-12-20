package com.example.marketapi.domain.order.entity;

import com.example.marketapi.domain.product.entity.Product;
import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Convert(converter = OrderItemStatusConverter.class)
    @Setter
    private OrderItemStatus status;
    private Long count;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderInfo orderInfo;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
