package kr.heyjyu.ofcors.application;

import kr.heyjyu.ofcors.models.Name;
import kr.heyjyu.ofcors.models.User;
import kr.heyjyu.ofcors.models.UserId;
import kr.heyjyu.ofcors.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ChangeNameServiceTest {
    private UserRepository userRepository;
    private ChangeNameService changeNameService;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        changeNameService = new ChangeNameService(userRepository);
    }

    @Test
    void changeName() {
        User user = User.fake();

        given(userRepository.findById(any()))
                .willReturn(Optional.of(user));

        Name name = new Name("홍길동길동");

        changeNameService.changeName(new UserId(1L), name);

        assertThat(user.getName()).isEqualTo(name);
    }
}
