package kr.heyjyu.ofcors.application;

import kr.heyjyu.ofcors.models.Email;
import kr.heyjyu.ofcors.models.Password;
import kr.heyjyu.ofcors.models.User;
import kr.heyjyu.ofcors.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class TrialLoginServiceTest {
    private TrialLoginService trialLoginService;
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        trialLoginService = new TrialLoginService(userRepository);
    }

    @Test
    void loginSuccess() {
        User user = User.fake();
        Email email = new Email("test@example.com");

        given(userRepository.findByEmail(any()))
                .willReturn(Optional.of(user));

        assertThat(trialLoginService.login().getEmail())
                .isEqualTo(email);
    }
}
