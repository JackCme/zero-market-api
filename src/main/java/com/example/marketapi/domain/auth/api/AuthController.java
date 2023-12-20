package com.example.marketapi.domain.auth.api;

import com.example.marketapi.global.exception.GlobalException;
import com.example.marketapi.global.exception.model.ResultCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @GetMapping("/denied")
    public void denied() {
        throw new GlobalException(ResultCode.ACCESS_DENIED);
    }

    @GetMapping("/unauthenticated")
    public void unauthenticated() {
        throw new GlobalException(ResultCode.UNAUTHENTICATED);
    }
}
