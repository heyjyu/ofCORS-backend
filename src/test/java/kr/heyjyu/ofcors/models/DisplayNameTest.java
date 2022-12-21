package kr.heyjyu.ofcors.models;

import kr.heyjyu.ofcors.exceptions.InvalidDisplayName;
import kr.heyjyu.ofcors.exceptions.InvalidEmail;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DisplayNameTest {
    @Test
    void creation() {
        assertDoesNotThrow(
                () -> new DisplayName("nickName")
        );
    }

    @Test
    void withShortDisplayName() {
        assertThrows(InvalidDisplayName.class, () -> {
            new DisplayName("jo");
        });
    }
}
