package com.example.marketapi.domain.product.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductException extends RuntimeException {
    private ErrorCode errorCode;
    private String errorMessage;

    public ProductException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }

    @Getter
    @AllArgsConstructor
    public enum ErrorCode {
        PRODUCT_NOT_FOUND(404, "상품이 존재하지 않습니다.")
        ,INSUFFICIENT_STOCK_EXCEPTION(400, "상품의 재고가 부족합니다.");

        private final int status;
        private final String description;
    }
}
