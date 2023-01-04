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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class GetAnswerServiceTest {
    private AnswerRepository answerRepository;
    private UserRepository userRepository;
    private GetAnswerService getAnswerService;

    @BeforeEach
    void setup() {
        answerRepository = mock(AnswerRepository.class);
        userRepository = mock(UserRepository.class);
        getAnswerService = new GetAnswerService(answerRepository, userRepository);
    }

    @Test
    void getAnswers() {
        given(answerRepository.findById(any()))
                .willReturn(Optional.of(Answer.fake()));

        given(userRepository.findById(any()))
                .willReturn(Optional.of(User.fake()));

        Long id = 1L;

        assertThat(getAnswerService.getAnswer(id))
                .isNotNull();
    }
}
