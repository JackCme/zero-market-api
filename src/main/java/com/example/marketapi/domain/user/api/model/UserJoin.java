package com.example.marketapi.domain.user.api.model;

import com.example.marketapi.domain.user.dto.UserAccountDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserJoin {
    @Getter
    @Setter
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
    @Setter
    @Builder
    public static class Response {
        private Long userId;
        private String email;

        public static Response from(UserAccountDto dto) {
            return Response.builder()
                    .userId(dto.getUid())
                    .email(dto.getEmail())
                    .build();
        }
    }
}
