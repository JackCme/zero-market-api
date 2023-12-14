package com.example.marketapi.domain.product.dto;

import com.example.marketapi.domain.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long productId;
    private String name;
    private Long inStock;
    private Long price;

    public static ProductDto fromEntity(Product product) {
        return ProductDto.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .inStock(product.getInStock())
                .price(product.getPrice())
                .build();
    }
}
