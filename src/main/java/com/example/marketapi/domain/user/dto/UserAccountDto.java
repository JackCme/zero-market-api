package com.example.marketapi.domain.user.dto;

import com.example.marketapi.domain.user.entity.UserAccount;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountDto {
    private Long uid;
    private String email;

    public static UserAccountDto fromEntity(UserAccount userAccount) {
        return UserAccountDto.builder()
                .uid(userAccount.getUserId())
                .email(userAccount.getEmail())
                .build();
    }
}
