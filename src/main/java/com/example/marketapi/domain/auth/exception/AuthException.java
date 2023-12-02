package com.example.marketapi.domain.auth.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class AuthException extends RuntimeException {
    private ErrorCode errorCode;
    private String errorMessage;

    public AuthException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }

    @Getter
    @AllArgsConstructor
    public enum ErrorCode {
        ACCESS_DENIED(401, "ACCESS_DENIED")
        , UNAUTHENTICATED(403, "UNAUTHENTICATED")
        ;
        private final int status;
        private final String description;
    }
}
