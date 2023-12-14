package com.example.marketapi.domain.product.service;

import com.example.marketapi.domain.product.dto.ProductDto;
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
        return null;
    }

    @Transactional
    public void decreaseProductStock(Long productId, Long byCount) {
    }

}
