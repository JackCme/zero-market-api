package com.example.marketapi.domain.user.exception;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAccountException extends RuntimeException {
    private ErrorCode errorCode;
    private String errorMessage;


    @Getter
    @AllArgsConstructor
    public enum ErrorCode {
        USER_ALREADY_EXISTS(400, "이미 사용자가 존재합니다.")
        , EMAIL_NOT_VALID(400, "올바른 이메일 형식이 아닙니다.")
        , PASSWORD_NOT_VALID(400, "비밀번호의 형식이 올바르지 않습니다.")
        ;
        private final int status;
        private final String description;
    }
}
