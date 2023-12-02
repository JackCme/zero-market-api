package com.example.marketapi.global.exception;

import com.example.marketapi.domain.auth.exception.AuthException;
import com.example.marketapi.domain.user.exception.UserAccountException;
import com.example.marketapi.global.exception.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalErrorAdvice {
    // Global Exception Handler
    @ExceptionHandler({UserAccountException.class, AuthException.class, Exception.class})
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        // TODO: 반복되는 코드가 많으니 OOP 를 활용해서 Exception 클래스들을 리팩토링 해보자
        if (e instanceof UserAccountException) {
            log.error("UserAccountException has occurred.", e);
            UserAccountException.ErrorCode error = ((UserAccountException) e).getErrorCode();
            return new ResponseEntity<>(
                    new ErrorResponse(error.getDescription(), error.toString()),
                    HttpStatus.valueOf(error.getStatus())
            );
        } else if (e instanceof AuthException) {
            log.error("AuthException has occurred.", e);
            AuthException.ErrorCode error = ((AuthException) e).getErrorCode();
            return new ResponseEntity<>(
                    new ErrorResponse(error.getDescription(), error.toString()),
                    HttpStatus.valueOf(error.getStatus())
            );
        } else {
            log.error("Exception has occurred.", e);
            HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<>(
                    new ErrorResponse(httpStatus.getReasonPhrase(), httpStatus.name()),
                    httpStatus
            );
        }

    }

    // 파라미터 @Valid 관련 에러에 대한 Handler
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException is occured.", e);

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(
                new ErrorResponse("올바른 요청이 아닙니다.", httpStatus.name()),
                httpStatus
        );
    }
}
