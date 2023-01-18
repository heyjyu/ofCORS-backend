package kr.heyjyu.ofcors.application;

import kr.heyjyu.ofcors.dtos.ProfileDto;
import kr.heyjyu.ofcors.models.User;
import kr.heyjyu.ofcors.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class EditProfileServiceTest {
    private UserRepository userRepository;
    private EditProfileService editProfileService;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        editProfileService = new EditProfileService(userRepository);
    }

    @Test
    void editProfile() {
        User user = User.fake();
        given(userRepository.findById(any()))
                .willReturn(Optional.of(user));

        Long userId = 1L;

        editProfileService.editProfile(userId, ProfileDto.fake());

        assertThat(user.getImageUrl().value()).isEqualTo(ProfileDto.fake().getImageUrl());
    }
}
