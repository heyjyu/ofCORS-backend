package kr.heyjyu.ofcors.application;

import kr.heyjyu.ofcors.models.Question;
import kr.heyjyu.ofcors.models.User;
import kr.heyjyu.ofcors.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class GetUsersServiceTest {
    private UserRepository userRepository;
    private GetUsersService getUsersService;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        getUsersService = new GetUsersService(userRepository);
    }

    @Test
    void getUsers() {
        given(userRepository.findAll(any(Pageable.class)))
                .willReturn(new PageImpl<>(List.of(User.fake())));

        given(userRepository.findById(any()))
                .willReturn(Optional.of(User.fake()));

        String sort = "like";
        String keyword = "";
        Integer count = 30;

        assertThat(getUsersService.getUsers(sort, keyword, count))
                .hasSize(1);
    }

    @Test
    void getUsersByKeyword() {
        given(userRepository.findAll(any(Specification.class), any(Pageable.class)))
                .willReturn(new PageImpl<>(List.of(User.fake())));

        given(userRepository.findById(any()))
                .willReturn(Optional.of(User.fake()));

        String sort = "like";
        String keyword = "web";
        Integer count = 30;

        assertThat(getUsersService.getUsers(sort, keyword, count))
                .hasSize(1);
    }
}
