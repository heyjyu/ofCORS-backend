package kr.heyjyu.ofcors.application;

import kr.heyjyu.ofcors.models.Answer;
import kr.heyjyu.ofcors.models.QuestionId;
import kr.heyjyu.ofcors.models.User;
import kr.heyjyu.ofcors.repositories.AnswerRepository;
import kr.heyjyu.ofcors.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class GetAnswersServiceTest {
    private AnswerRepository answerRepository;
    private UserRepository userRepository;
    private GetAnswersService getAnswersService;

    @BeforeEach
    void setup() {
        answerRepository = mock(AnswerRepository.class);
        userRepository = mock(UserRepository.class);
        getAnswersService = new GetAnswersService(answerRepository, userRepository);
    }

    @Test
    void getAnswers() {
        given(answerRepository.findAllByQuestionId(any()))
                .willReturn(List.of(Answer.fake()));

        given(userRepository.findById(any()))
                .willReturn(Optional.of(User.fake()));

        QuestionId questionId = new QuestionId(1L);

        assertThat(getAnswersService.getAnswers(questionId))
                .hasSize(1);
    }
}
