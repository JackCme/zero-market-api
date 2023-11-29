package com.example.marketapi.domain.user.service;

import com.example.marketapi.domain.user.dto.UserAccountDto;
import com.example.marketapi.domain.user.entity.UserAccount;
import com.example.marketapi.domain.user.exception.UserAccountException;
import com.example.marketapi.domain.user.repository.UserAccountRepository;
import com.example.marketapi.global.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAccountService {
    private final UserAccountRepository userAccountRepository;

    public UserAccountDto createUserAccount(String email, String password) {
        validateCreateUserAccount(email, password);

        UserAccount userAccount = userAccountRepository.save(UserAccount.builder()
                .email(email)
                .password(password)
                .build());

        return UserAccountDto.fromEntity(userAccount);
    }

    private void validateCreateUserAccount(String email, String password) {
        if (!ValidationUtil.isValidEmail(email)) {
            throw new UserAccountException(UserAccountException.ErrorCode.EMAIL_NOT_VALID);
        }
        if (!ValidationUtil.isValidPassword(password)) {
            throw new UserAccountException(UserAccountException.ErrorCode.PASSWORD_NOT_VALID);
        }
        if (userAccountRepository.findUserAccountByEmail(email).isPresent()) {
            throw new UserAccountException(UserAccountException.ErrorCode.USER_ALREADY_EXISTS);
        }
    }
}
