package kr.heyjyu.ofcors.models;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {
    @Test
    void changePassword() {
        PasswordEncoder passwordEncoder = new Argon2PasswordEncoder(16, 32, 1, 1 << 14, 2);

        User user = User.fake();

        user.changePassword(new Password("Abcdef1!"), passwordEncoder);

        assertThat(user.getPassword()).isNotNull();
        assertThat(user.getPassword()).isNotEqualTo(new Password("Abcdef1!"));
    }

    @Test
    void setInitialPoint() {
        User user = new User();

        user.setInitialPoint();

        assertThat(user.getPoints()).isEqualTo(User.INITIAL_POINT);
    }

    @Test
    void authenticate() {
        PasswordEncoder passwordEncoder = new Argon2PasswordEncoder(16, 32, 1, 1 << 14, 2);

        User user = new User();

        user.changePassword(new Password("Abcdef1!"), passwordEncoder);

        assertThat(user.authenticate(new Password("Abcdef1!"), passwordEncoder)).isTrue();
        assertThat(user.authenticate(new Password("Abcdef1!!"), passwordEncoder)).isFalse();
    }
}
