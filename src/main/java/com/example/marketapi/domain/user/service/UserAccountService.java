package com.example.marketapi.domain.user.service;

import com.example.marketapi.domain.cart.service.CartService;
import com.example.marketapi.domain.user.dto.UserAccountDto;
import com.example.marketapi.domain.user.entity.UserAccount;
import com.example.marketapi.domain.user.exception.UserAccountException;
import com.example.marketapi.domain.user.repository.UserAccountRepository;
import com.example.marketapi.global.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserAccountService implements UserDetailsService {
    private final UserAccountRepository userAccountRepository;
    // SecurityConfig class 에 Bean 으로 등록해놓은 PasswordEncoder
    private final PasswordEncoder passwordEncoder;
    private final CartService cartService;

    @Transactional
    public UserAccountDto createUserAccount(String email, String password) {
        validateCreateUserAccount(email, password);

        UserAccount userAccount = userAccountRepository.save(UserAccount.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .build());

        cartService.createUserCart(userAccount.getUserId());

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

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userAccountRepository.findUserAccountByEmail(username)
                .orElseThrow(() -> new UserAccountException(UserAccountException.ErrorCode.USER_NOT_FOUND));
    }
}
