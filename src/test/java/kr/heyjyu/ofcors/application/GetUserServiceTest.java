package kr.heyjyu.ofcors.application;

import kr.heyjyu.ofcors.models.User;
import kr.heyjyu.ofcors.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class GetUserServiceTest {
    private GetUserService getUserService;
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        getUserService = new GetUserService(userRepository);
    }

    @Test
    void getUser() {
        given(userRepository.findById(1L))
                .willReturn(Optional.of(User.fake()));

        User user = getUserService.getUser(1L);

        assertThat(user).isNotNull();
    }
}
