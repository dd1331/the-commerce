package com.example.thecommerce.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordTest {

    @Test
    @DisplayName("원본 비밀번호화 암호화된 비밀번호는 서로 다르고 matches 메소드로 일치여부를 확인할 수 있다")
    public void testPasswordEncryption() {
        String rawPassword = "testPassword";

        Password password = new Password(rawPassword);

        assertNotEquals(rawPassword, password.getPassword());
        assertTrue(password.matches(rawPassword));
        assertFalse(password.matches("wrong"));
    }
}