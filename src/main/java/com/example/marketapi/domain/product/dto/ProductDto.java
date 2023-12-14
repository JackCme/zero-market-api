package com.example.marketapi.domain.product.dto;

import com.example.marketapi.domain.product.entity.Product;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long productId;
    private String name;
    @Setter
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

    public static Product toEntity(ProductDto productDto) {
        return Product.builder()
                .productId(productDto.productId)
                .name(productDto.name)
                .inStock(productDto.inStock)
                .price(productDto.getPrice())
                .build();
    }

}
