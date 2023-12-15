package com.example.marketapi.domain.cart.exception;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartException extends RuntimeException {
    private ErrorCode errorCode;
    private String errorMessage;

    public CartException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }

    @Getter
    @AllArgsConstructor
    public enum ErrorCode {
        CART_INFO_ALREADY_EXISTS(400, "이미 장바구니가 존재합니다.")
        ;
        private final int status;
        private final String description;
    }
}
