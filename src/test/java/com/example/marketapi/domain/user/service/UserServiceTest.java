package com.example.marketapi.domain.user.service;

import com.example.marketapi.domain.user.dto.UserAccountDto;
import com.example.marketapi.domain.user.entity.UserAccount;
import com.example.marketapi.domain.user.exception.UserAccountException;
import com.example.marketapi.domain.user.repository.UserAccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserAccountRepository userAccountRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserAccountService userAccountService;

    @Test
    @DisplayName("사용자 계정정보 생성 - 성공")
    void createUserAccountSuccess() {
        // TODO: 장바구니 오류없이 생성되었는지 테스트 할 것
        // Given
        Long userId = 1L;
        String email = "jack@jack.com";
        String password = "1q2w3e4r@@R";
        UserAccount createdAccount = UserAccount.builder()
                .userId(userId)
                .email(email)
                .password(password)
                .build();
        given(userAccountRepository.findUserAccountByEmail(anyString())).willReturn(Optional.empty());
        given(userAccountRepository.save(any())).willReturn(createdAccount);
        given(passwordEncoder.encode(password)).willReturn(new BCryptPasswordEncoder().encode(password));

        // When
        UserAccountDto userAccountDto = userAccountService.createUserAccount(email, password);

        // Then
        assertNotNull(userAccountDto);
        assertEquals(email, userAccountDto.getEmail());
        assertEquals(userId, userAccountDto.getUid());
    }

    @DisplayName("이미 존재하는 계정정보 - 실패")
    @Test
    void createUserAccountFailed_UserAlreadyExists() {
        // Given
        String email = "jack@jack.com";
        String password = "1q2w3e4rQQ!";
        UserAccount user = UserAccount.builder()
                .email(email)
                .password(password)
                .build();
        given(userAccountRepository.findUserAccountByEmail(anyString()))
                .willReturn(Optional.of(user));
        // When
        UserAccountException userAccountException = assertThrows(UserAccountException.class, () -> userAccountService.createUserAccount(email, password));

        // Then
        assertEquals(UserAccountException.ErrorCode.USER_ALREADY_EXISTS, userAccountException.getErrorCode());
        assertEquals(UserAccountException.ErrorCode.USER_ALREADY_EXISTS.getDescription(), userAccountException.getErrorMessage());
    }

    @DisplayName("올바르지 않은 이메일 형식 - 실패")
    @Test
    void createUserAccountFailed_EmailNotValid() {
        // Given
        String invalidEmail = "jack";
        // When
        UserAccountException userAccountException = assertThrows(UserAccountException.class, () -> userAccountService.createUserAccount(invalidEmail, "1234"));

        // Then
        assertEquals(UserAccountException.ErrorCode.EMAIL_NOT_VALID, userAccountException.getErrorCode());
        assertEquals(UserAccountException.ErrorCode.EMAIL_NOT_VALID.getDescription(), userAccountException.getErrorMessage());
    }

    @DisplayName("올바르지 않은 비밀번호 형식 - 실패")
    @Test
    void createUserAccountFailed_PasswordNotValid() {
        // Given
        String invalidPassword = "1234";
        // When
        UserAccountException userAccountException = assertThrows(UserAccountException.class, () -> userAccountService.createUserAccount("jack@jack.com", invalidPassword));
        // Then
        assertEquals(UserAccountException.ErrorCode.PASSWORD_NOT_VALID, userAccountException.getErrorCode());
        assertEquals(UserAccountException.ErrorCode.PASSWORD_NOT_VALID.getDescription(), userAccountException.getErrorMessage());
    }
}