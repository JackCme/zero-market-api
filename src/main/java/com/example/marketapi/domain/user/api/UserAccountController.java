package com.example.marketapi.domain.user.api;

import com.example.marketapi.domain.user.api.model.UserJoin;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserAccountController {
    @PostMapping("/join")
    public UserJoin.Response join(@RequestBody @Valid UserJoin.Request requestBody) {
        return null;
    }
}
