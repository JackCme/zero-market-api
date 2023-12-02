package com.example.marketapi.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtSecurityConfig jwtSecurityConfig;

    // PasswordEncoder는 BCryptPasswordEncoder를 사용
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // token을 사용하는 방식이기 때문에 csrf를 disable합니다.
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                // 세션을 사용하지 않기 때문에 STATELESS로 설정
                .sessionManagement(configurer ->
                        configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // 시큐리티 익셉션 핸들러 등록
                .exceptionHandling(configurer ->
                        configurer.accessDeniedHandler(jwtAccessDeniedHandler)
                                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )
                // HttpServletRequest를 사용하는 요청들에 대한 접근제한을 설정하겠다.
                .authorizeHttpRequests(matcher ->
                        matcher.antMatchers("/user/login").permitAll() // 로그인 api
                                .antMatchers("/user/join").permitAll() // 회원가입 api
                                .antMatchers("/auth/**").permitAll()
                                .anyRequest().authenticated() // 그 외 인증 없이 접근X
                )
                // 필터체인 적용 - JwtFilter를 addFilterBefore로 등록했던 JwtSecurityConfig class 적용
                .apply(jwtSecurityConfig);

        return httpSecurity.build();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        jwtAccessDeniedHandler.setErrorURL("/auth/denied");
        return jwtAccessDeniedHandler;
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        jwtAuthenticationEntryPoint.setErrorURL("/auth/unauthenticated");
        return jwtAuthenticationEntryPoint;
    }
}
