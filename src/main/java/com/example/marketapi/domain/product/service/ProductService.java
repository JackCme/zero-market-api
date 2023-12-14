package com.example.marketapi.domain.product.service;

import com.example.marketapi.domain.product.dto.ProductDto;
import com.example.marketapi.domain.product.entity.Product;
import com.example.marketapi.domain.product.exception.ProductException;
import com.example.marketapi.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
    public ProductDto getProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductException(ProductException.ErrorCode.PRODUCT_NOT_FOUND));
        return ProductDto.fromEntity(product);
    }

    @Transactional
    public void decreaseProductStock(Long productId, Long byCount) {
        ProductDto productDto = getProduct(productId);
        if (productDto.getInStock() < byCount) {
            throw new ProductException(ProductException.ErrorCode.INSUFFICIENT_STOCK_EXCEPTION);
        }
        productDto.setInStock(productDto.getInStock() - byCount);

        productRepository.save(ProductDto.toEntity(productDto));
    }

}
