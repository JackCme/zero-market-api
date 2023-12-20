package com.example.marketapi.domain.product.service;

import com.example.marketapi.domain.product.dto.ProductDto;
import com.example.marketapi.domain.product.entity.Product;
import com.example.marketapi.global.exception.GlobalException;
import com.example.marketapi.domain.product.repository.ProductRepository;
import com.example.marketapi.global.exception.model.ResultCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("상품정보 조회 성공")
    void getProductSuccess() {
        // Given
        Long productId = 1L;
        String productName = "샘플상품";
        Long inStock = 10L;
        Long price = 10_000L;
        Product product = Product.builder()
                .name(productName)
                .inStock(inStock)
                .price(price)
                .productId(productId)
                .build();
        given(productRepository.findById(anyLong())).willReturn(Optional.of(product));
        // When
        ProductDto productDto = productService.getProduct(productId);
        // Then
        assertEquals(productId, productDto.getProductId());
        assertEquals(productName, productDto.getName());
        assertEquals(inStock, productDto.getInStock());
        assertEquals(price, productDto.getPrice());
    }

    @Test
    @DisplayName("상품정보 조회 실패 - 상품이 존재하지 않음")
    void getProductFailed_ProductNotFound() {
        // Given
        Long productId = 1L;
        given(productRepository.findById(anyLong())).willReturn(Optional.empty());
        // When
        GlobalException productException = assertThrows(GlobalException.class, () -> productService.getProduct(productId));
        // Then
        assertEquals(ResultCode.PRODUCT_NOT_FOUND, productException.getResultCode());
        assertEquals(ResultCode.PRODUCT_NOT_FOUND.getDescription(), productException.getResultCode().getDescription());
    }

    @Test
    @DisplayName("상품 재고차감 성공")
    void decreaseProductStockSuccess() {
        // Given
        Long productId = 1L;
        String productName = "샘플상품";
        Long inStock = 10L;
        Long price = 10_000L;
        Product product = Product.builder()
                .name(productName)
                .inStock(inStock)
                .price(price)
                .productId(productId)
                .build();
        Long decreaseStockBy = 10L;
        Product newProduct = Product.builder()
                .inStock(0L)
                .build();
        given(productRepository.findById(anyLong())).willReturn(Optional.of(product));
        given(productRepository.save(any())).willReturn(newProduct);
        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        // When
        productService.decreaseProductStock(productId, decreaseStockBy);
        // Then
        verify(productRepository, times(1)).save(captor.capture());
        assertEquals(product.getInStock() - decreaseStockBy, captor.getValue().getInStock());
    }

    @Test
    @DisplayName("상품 재고차감 실패 - 재고 없음")
    void decreaseProductStockFailed_InsufficientStockException() {
        // Given
        Long productId = 1L;
        String productName = "샘플상품";
        Long inStock = 10L;
        Long price = 10_000L;
        Product product = Product.builder()
                .name(productName)
                .inStock(inStock)
                .price(price)
                .productId(productId)
                .build();
        Long decreaseStockBy = 11L;
        given(productRepository.findById(anyLong())).willReturn(Optional.of(product));
        // When
        GlobalException productException = assertThrows(GlobalException.class, () -> productService.decreaseProductStock(productId, decreaseStockBy));
        // Then
        assertEquals(ResultCode.INSUFFICIENT_STOCK_EXCEPTION, productException.getResultCode());
        assertEquals(ResultCode.INSUFFICIENT_STOCK_EXCEPTION.getDescription(), productException.getResultCode().getDescription());
    }
}
