package com.example.marketapi.domain.user.api;

import com.example.marketapi.domain.user.exception.UserAccountException;
import com.example.marketapi.global.exception.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class UserAccountControllerAdvice {
    @ExceptionHandler(UserAccountException.class)
    public ResponseEntity<ErrorResponse> handleUserAccountException(UserAccountException e) {
        log.error("UserAccountException is occured.", e);

        UserAccountException.ErrorCode error = e.getErrorCode();
        return new ResponseEntity<>(
                new ErrorResponse(error.getDescription(), error.toString()),
                HttpStatus.valueOf(error.getStatus())
        );
    }
}
