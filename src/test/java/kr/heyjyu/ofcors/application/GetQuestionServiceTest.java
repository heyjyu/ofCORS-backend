package kr.heyjyu.ofcors.application;

import kr.heyjyu.ofcors.models.Question;
import kr.heyjyu.ofcors.models.User;
import kr.heyjyu.ofcors.repositories.QuestionRepository;
import kr.heyjyu.ofcors.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class GetQuestionServiceTest {
    private QuestionRepository questionRepository;
    private UserRepository userRepository;
    private GetQuestionService getQuestionService;

    @BeforeEach
    void setup() {
        questionRepository = mock(QuestionRepository.class);
        userRepository = mock(UserRepository.class);
        getQuestionService = new GetQuestionService(questionRepository, userRepository);
    }

    @Test
    void getQuestion() {
        Long id = 1L;

        given(questionRepository.findById(1L))
                .willReturn(Optional.of(Question.fake()));

        given(userRepository.findById(any()))
                .willReturn(Optional.of(User.fake()));

        assertThat(getQuestionService.getQuestion(id))
                .isNotNull();
    }
}
