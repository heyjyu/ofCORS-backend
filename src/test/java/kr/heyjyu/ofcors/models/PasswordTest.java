package kr.heyjyu.ofcors.models;

import kr.heyjyu.ofcors.exceptions.InvalidEmail;
import kr.heyjyu.ofcors.exceptions.InvalidPassword;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class PasswordTest {
    @Test
    void creation() {
        assertDoesNotThrow(() -> {
            new Password("Abcdef1!");
        });
    }

    @Test
    void shortPassword() {
        assertThrows(InvalidPassword.class, () -> {
            new Password("Abcde1!");
        });
    }

    @Test
    void passwordWithoutLowerCase() {
        assertThrows(InvalidPassword.class, () -> {
            new Password("ABCDEF1!");
        });
    }

    @Test
    void passwordWithoutUpperCase() {
        assertThrows(InvalidPassword.class, () -> {
            new Password("abcdef1!");
        });
    }

    @Test
    void passwordWithoutNumber() {
        assertThrows(InvalidPassword.class, () -> {
            new Password("Abcdefg!");
        });
    }

    @Test
    void passwordWithoutSpecialCharacter() {
        assertThrows(InvalidPassword.class, () -> {
            new Password("Abcdefg1");
        });
    }
}
