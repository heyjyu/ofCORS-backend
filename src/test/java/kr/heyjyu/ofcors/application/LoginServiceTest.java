package kr.heyjyu.ofcors.application;

import kr.heyjyu.ofcors.exceptions.LoginFailed;
import kr.heyjyu.ofcors.models.Email;
import kr.heyjyu.ofcors.models.Password;
import kr.heyjyu.ofcors.models.User;
import kr.heyjyu.ofcors.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class LoginServiceTest {
    private LoginService loginService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        passwordEncoder = new Argon2PasswordEncoder(16, 32, 1, 1 << 14, 2);
        userRepository = mock(UserRepository.class);
        loginService = new LoginService(userRepository, passwordEncoder);
    }

    @Test
    void loginSuccess() {
        Email email = new Email("test@example.com");
        Password password = new Password("Abcdef1!");

        User user = User.fake();
        user.changePassword(password, passwordEncoder);

        given(userRepository.findByEmail(email))
                .willReturn(Optional.of(user));

        assertThat(loginService.login(email, password).getEmail())
                .isEqualTo(email);
    }

    @Test
    void loginWithWrongUsername() {
        Email email = new Email("test@example.com");
        Password password = new Password("Abcdef1!");

        assertThrows(LoginFailed.class,
                () -> loginService.login(email, password));
    }

    @Test
    void loginWithWrongPassword() {
        Email email = new Email("test@example.com");
        Password password = new Password("Abcdef1!");

        User user = User.fake();
        user.changePassword(new Password("ABCdef1!"), passwordEncoder);

        given(userRepository.findByEmail(email))
                .willReturn(Optional.of(user));

        assertThrows(LoginFailed.class,
                () -> loginService.login(email, password));
    }
}
