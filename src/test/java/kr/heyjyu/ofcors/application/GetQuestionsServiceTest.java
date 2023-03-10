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

class GetQuestionsServiceTest {
    private QuestionRepository questionRepository;
    private UserRepository userRepository;
    private GetQuestionsService getQuestionsService;

    @BeforeEach
    void setup() {
        questionRepository = mock(QuestionRepository.class);
        userRepository = mock(UserRepository.class);
        getQuestionsService = new GetQuestionsService(questionRepository, userRepository);
    }

    @Test
    void getTopQuestions() {
        given(questionRepository.findAll(any(Specification.class), any(Pageable.class)))
                .willReturn(new PageImpl<>(List.of(Question.fake())));

        given(userRepository.findById(any()))
                .willReturn(Optional.of(User.fake()));

        String sort = "like";
        String period = "week";
        String status = "open";
        String keyword = "";
        Integer size = 30;

        assertThat(getQuestionsService.getQuestions(sort, period, status, keyword, size))
                .hasSize(1);
    }

    @Test
    void getSearchResult() {
        given(userRepository.findById(any()))
                .willReturn(Optional.of(User.fake()));

        given(questionRepository.findAll(any(Specification.class), any(Pageable.class)))
                .willReturn(new PageImpl<>(List.of(Question.fake())));

        String sort = "like";
        String period = "";
        String status = "closed";
        String keyword = "CORS";
        Integer size = 30;

        assertThat(getQuestionsService.getQuestions(sort, period, status, keyword, size))
                .hasSize(1);
    }
}
