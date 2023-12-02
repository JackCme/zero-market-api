package com.example.marketapi.global.util;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidationUtilTest {

    @Test
    void isValidEmail() {
        List<String> testList = Arrays.asList(
                "jack@jack.com", "valid.a@aaa.com"
        );

        for (String sut: testList) {
            assertTrue(ValidationUtil.isValidEmail(sut));
        }
    }

    @Test
    void isValidPassword() {
        List<String> pwList = Arrays.asList(
                "1Q2w3e4r@@", "12345QWertt!@#", "$1-()0+=-28766AAa"
        );
        for (String sut : pwList) {
            assertTrue(ValidationUtil.isValidPassword(sut));
        }
    }
}