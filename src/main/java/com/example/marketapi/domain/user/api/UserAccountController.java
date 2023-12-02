package com.example.marketapi.domain.user.api;

import com.example.marketapi.domain.user.api.model.UserJoin;
import com.example.marketapi.domain.user.api.model.UserLogin;
import com.example.marketapi.domain.user.service.UserAccountService;
import com.example.marketapi.global.config.JwtFilter;
import com.example.marketapi.global.config.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserAccountController {
    private final UserAccountService userAccountService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/join")
    public UserJoin.Response join(@RequestBody @Valid UserJoin.Request requestBody) {
        return UserJoin.Response.from(
            userAccountService.createUserAccount(requestBody.getEmail(), requestBody.getPassword())
        );
    }

    @PostMapping("/login")
    public ResponseEntity<UserLogin.Response> login(@RequestBody @Valid UserLogin.Request requestBody) {
        // 스프링 시큐리티 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(requestBody.getEmail(), requestBody.getPassword());

        // authenticate 메소드가 실행이 될 때 UserDetailsService class의 loadUserByUsername 메소드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // 해당 객체를 SecurityContextHolder에 저장하고
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // authentication 객체를 createToken 메소드를 통해서 JWT Token을 생성
        String jwtToken = jwtTokenProvider.createToken(authentication);
        HttpHeaders httpHeaders = new HttpHeaders();
        // response header에 jwt token에 넣어줌
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwtToken);

        // tokenDto를 이용해 response body에도 넣어서 리턴
        return new ResponseEntity<>(
                new UserLogin.Response(jwtToken),
                httpHeaders,
                HttpStatus.OK
        );


    }
}
