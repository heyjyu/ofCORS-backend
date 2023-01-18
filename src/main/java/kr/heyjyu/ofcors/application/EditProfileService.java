package kr.heyjyu.ofcors.application;

import jakarta.transaction.Transactional;
import kr.heyjyu.ofcors.dtos.ProfileDto;
import kr.heyjyu.ofcors.exceptions.UserNotFound;
import kr.heyjyu.ofcors.models.About;
import kr.heyjyu.ofcors.models.DisplayName;
import kr.heyjyu.ofcors.models.ImageUrl;
import kr.heyjyu.ofcors.models.Tag;
import kr.heyjyu.ofcors.models.User;
import kr.heyjyu.ofcors.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class EditProfileService {
    private UserRepository userRepository;

    public EditProfileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void editProfile(Long userId, ProfileDto profileDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFound(userId));

        DisplayName displayName = new DisplayName(profileDto.getDisplayName());
        About about = new About(profileDto.getAbout());
        ImageUrl imageUrl = new ImageUrl(profileDto.getImageUrl());
        Optional<Set<String>> tagsOptional = Optional.ofNullable(profileDto.getTags());
        Set<Tag> tags = tagsOptional.isPresent()
                ? tagsOptional.get().stream().map(tag -> new Tag(tag))
                .collect(Collectors.toSet())
                : new HashSet<>();

        user.editProfile(displayName, about, imageUrl, tags);
    }
}
