package kr.heyjyu.ofcors.application;

import jakarta.transaction.Transactional;
import kr.heyjyu.ofcors.dtos.AuthorDto;
import kr.heyjyu.ofcors.dtos.QuestionDto;
import kr.heyjyu.ofcors.dtos.UserDto;
import kr.heyjyu.ofcors.exceptions.UserNotFound;
import kr.heyjyu.ofcors.models.AnswerId;
import kr.heyjyu.ofcors.models.LikeUserId;
import kr.heyjyu.ofcors.models.Tag;
import kr.heyjyu.ofcors.models.User;
import kr.heyjyu.ofcors.repositories.UserRepository;
import kr.heyjyu.ofcors.specifications.QuestionSpecification;
import kr.heyjyu.ofcors.specifications.UserSpecification;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class GetUsersService {
    private UserRepository userRepository;

    public GetUsersService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> getUsers(String sort, String keyword, Integer size) {
        // TODO: get page from request
        Integer page = 1;

        Sort sortBy = Sort.by("countOfLikes").descending();

        Pageable pageable = PageRequest.of(page - 1, size, sortBy);

        if (!keyword.equals("")) {
            Specification<User> specification = UserSpecification.likeDisplayNameOrTag(keyword);

            return userRepository.findAll(specification, pageable)
                    .stream()
                    .map(user -> user.toDto())
                    .collect(Collectors.toList());
        }

        return userRepository.findAll(pageable)
                .stream()
                .map(user -> user.toDto())
                .collect(Collectors.toList());
    }
}
