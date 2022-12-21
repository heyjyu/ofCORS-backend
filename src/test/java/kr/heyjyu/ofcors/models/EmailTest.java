package kr.heyjyu.ofcors.models;

import kr.heyjyu.ofcors.exceptions.InvalidEmail;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {
    @Test
    void creation() {
        assertDoesNotThrow(
                () -> new Email("test@email.com")
        );
    }

    @Test
    void withInvalidEmail() {
        assertThrows(InvalidEmail.class, () -> {
            new Email("incorrectEmail");
        });
    }
}
