package com.example.marketapi.domain.user.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserLogin {
    @Getter
    @AllArgsConstructor
    public static class Request {
        @NotNull
        @NotBlank
        private String email;
        @NotNull
        @NotBlank
        private String password;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private String token;
    }
}
