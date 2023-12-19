package com.example.marketapi.global.exception.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private String errorCode;
    private String errorMessage;

    public ErrorResponse(ResultCode resultCode) {
        errorCode = "ERR-" + resultCode.getStatusCode();
        errorMessage = resultCode.getDescription();
    }
}
