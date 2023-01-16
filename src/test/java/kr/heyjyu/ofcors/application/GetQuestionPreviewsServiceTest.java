package kr.heyjyu.ofcors.application;

import kr.heyjyu.ofcors.models.Question;
import kr.heyjyu.ofcors.models.User;
import kr.heyjyu.ofcors.repositories.QuestionRepository;
import kr.heyjyu.ofcors.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class GetQuestionPreviewsServiceTest {
    private QuestionRepository questionRepository;
    private UserRepository userRepository;
    private GetQuestionPreviewsService getQuestionPreviewsService;

    @BeforeEach
    void setup() {
        questionRepository = mock(QuestionRepository.class);
        userRepository = mock(UserRepository.class);
        getQuestionPreviewsService = new GetQuestionPreviewsService(questionRepository, userRepository);
    }

    @Test
    void getQuestionPreviews() {
        given(userRepository.findById(any()))
                .willReturn(Optional.of(User.fake()));

        given(questionRepository.findAll(any(Specification.class), any(Pageable.class)))
                .willReturn(new PageImpl<>(List.of(Question.fake())));

        Long userId = 1L;

        assertThat(getQuestionPreviewsService.getQuestionPreviews(userId))
                .hasSize(1);
    }
}
