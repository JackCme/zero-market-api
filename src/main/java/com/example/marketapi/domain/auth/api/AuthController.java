package com.example.marketapi.domain.auth.api;

import com.example.marketapi.domain.auth.exception.AuthException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @GetMapping("/denied")
    public void denied() {
        throw new AuthException(AuthException.ErrorCode.ACCESS_DENIED);
    }

    @GetMapping("/unauthenticated")
    public void unauthenticated() {
        throw new AuthException(AuthException.ErrorCode.UNAUTHENTICATED);
    }
}
