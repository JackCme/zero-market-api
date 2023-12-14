package com.example.marketapi.domain.user.api;

import com.example.marketapi.domain.user.api.model.UserJoin;
import com.example.marketapi.domain.user.dto.UserAccountDto;
import com.example.marketapi.domain.user.exception.UserAccountException;
import com.example.marketapi.domain.user.service.UserAccountService;
import com.example.marketapi.global.config.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = UserAccountController.class, includeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
                JwtTokenProvider.class, JwtAuthenticationEntryPoint.class, JwtAccessDeniedHandler.class, JwtSecurityConfig.class
        })
})
@Import(SecurityConfig.class)
class UserAccountControllerTest {
    @MockBean
    private UserAccountService userAccountService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("회원가입 성공")
    void joinSuccess() throws Exception {
        // Given
        String email = "jack@jack.com";
        String password = "1q2w3e4r!Q";
        given(userAccountService.createUserAccount(anyString(), anyString()))
                .willReturn(UserAccountDto.builder()
                        .uid(1L)
                        .email(email)
                        .build());
        // When

        // Then
        mockMvc.perform(post("/user/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new UserJoin.Request(email, password))
                        ))
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.email").value(email))
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입 실패 - 유효한 이메일 형식 아님")
    void joinFailed_EmailNotValid() throws Exception {
        // Given
        given(userAccountService.createUserAccount(anyString(), anyString()))
                .willThrow(new UserAccountException(UserAccountException.ErrorCode.EMAIL_NOT_VALID));
        // When
        // Then
        mockMvc.perform(post("/user/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new UserJoin.Request("jack", "1234")
                        ))
                )
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errorCode").value("EMAIL_NOT_VALID"))
                .andExpect(jsonPath("$.errorMessage").value("올바른 이메일 형식이 아닙니다."));

    }

    @Test
    @DisplayName("회원가입 실패 - 유효한 비밀번호 형식 아님")
    void joinFailed_PasswordNotValid() throws Exception {
        // Given
        given(userAccountService.createUserAccount(anyString(), anyString()))
                .willThrow(new UserAccountException(UserAccountException.ErrorCode.PASSWORD_NOT_VALID));
        // When
        // Then
        mockMvc.perform(post("/user/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new UserJoin.Request("jack@jack.com", "1234")
                        ))
                )
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errorCode").value("PASSWORD_NOT_VALID"))
                .andExpect(jsonPath("$.errorMessage").value("비밀번호의 형식이 올바르지 않습니다."));
    }

    @Test
    @DisplayName("회원가입 실패 - 회원 이미 존재")
    void joinFailed_UserAlreadyExists() throws Exception {
        // Given
        given(userAccountService.createUserAccount(anyString(), anyString()))
                .willThrow(new UserAccountException(UserAccountException.ErrorCode.USER_ALREADY_EXISTS));
        // When
        // Then
        mockMvc.perform(post("/user/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new UserJoin.Request("jack@jack.com", "1q2w3e4r!Q")
                        ))
                )
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errorCode").value("USER_ALREADY_EXISTS"))
                .andExpect(jsonPath("$.errorMessage").value("이미 사용자가 존재합니다."));

    }

    @Test
    @DisplayName("회원가입 실패 - 올바른 요청이 아닐 때")
    void joinFailed_InvalidBodyParameters() throws Exception {
        // When
        // Then
        mockMvc.perform(post("/user/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"email\":  null }")
                )
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errorCode").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.errorMessage").value("올바른 요청이 아닙니다."));
    }
}