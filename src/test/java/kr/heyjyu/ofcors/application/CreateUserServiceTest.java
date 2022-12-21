package kr.heyjyu.ofcors.application;

import kr.heyjyu.ofcors.exceptions.ExistingEmail;
import kr.heyjyu.ofcors.models.DisplayName;
import kr.heyjyu.ofcors.models.Email;
import kr.heyjyu.ofcors.models.Password;
import kr.heyjyu.ofcors.models.Points;
import kr.heyjyu.ofcors.models.User;
import kr.heyjyu.ofcors.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CreateUserServiceTest {
    private CreateUserService createUserService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = new Argon2PasswordEncoder(16, 32, 1, 1 << 14, 2);
        createUserService = new CreateUserService(userRepository, passwordEncoder);
    }

    @Test
    void create() {
        DisplayName displayName = new DisplayName("joo");
        Email email = new Email("test@example.com");
        Password password = new Password("Abcdef1!");

        User user = createUserService
                .create(displayName, email, password);

        assertThat(user).isNotNull();
        assertThat(user.getPoints()).isEqualTo(User.INITIAL_POINT);
        verify(userRepository).save(user);
    }

    @Test
    void createWithExistingUsername() {
        DisplayName displayName = new DisplayName("joo");
        Email email = new Email("test@example.com");
        Password password = new Password("Abcdef1!");

        given(userRepository.existsByEmail(email))
                .willReturn(true);

        assertThrows(ExistingEmail.class,
                () -> createUserService.create(displayName, email, password));
    }
}
