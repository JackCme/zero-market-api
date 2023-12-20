package com.example.marketapi.global.exception;

import com.example.marketapi.global.exception.model.ErrorResponse;
import com.example.marketapi.global.exception.model.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalErrorAdvice {
    // Global Exception Handler
    @ExceptionHandler({GlobalException.class})
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        // early return
        if (e instanceof GlobalException) {
            ResultCode resultCode = ((GlobalException) e).getResultCode();
            log.error("API Exception has occurred. {}", resultCode);
            return new ResponseEntity<>(
                    new ErrorResponse(resultCode),
                    resultCode.getHttpStatus()
            );
        }

        log.error("Exception has occurred.", e);
        ResultCode internalServerError = ResultCode.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(
                new ErrorResponse(internalServerError),
                internalServerError.getHttpStatus()
        );

    }

    // 파라미터 @Valid 관련 에러에 대한 Handler
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException is occured.", e);

        ResultCode invalidRequestParameter = ResultCode.INVALID_REQUEST_PARAMETER;
        return new ResponseEntity<>(
                new ErrorResponse(invalidRequestParameter),
                invalidRequestParameter.getHttpStatus()
        );
    }
}
