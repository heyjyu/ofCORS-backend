package kr.heyjyu.ofcors.application;

import kr.heyjyu.ofcors.models.Answer;
import kr.heyjyu.ofcors.models.Question;
import kr.heyjyu.ofcors.models.User;
import kr.heyjyu.ofcors.repositories.AnswerRepository;
import kr.heyjyu.ofcors.repositories.QuestionRepository;
import kr.heyjyu.ofcors.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class GetAnswerPreviewsServiceTest {
    private AnswerRepository answerRepository;
    private UserRepository userRepository;
    private QuestionRepository questionRepository;
    private GetAnswerPreviewsService getAnswerPreviewsService;

    @BeforeEach
    void setup() {
        answerRepository = mock(AnswerRepository.class);
        userRepository = mock(UserRepository.class);
        questionRepository = mock(QuestionRepository.class);

        getAnswerPreviewsService = new GetAnswerPreviewsService(answerRepository, userRepository, questionRepository);
    }

    @Test
    void getAnswerPreviews() {
        given(answerRepository.findAllByAuthorId(any(), any()))
                .willReturn(List.of(Answer.fake()));

        given(userRepository.findById(any()))
                .willReturn(Optional.of(User.fake()));

        given(questionRepository.findById(any()))
                .willReturn(Optional.of(Question.fake()));

        Long userId = 1L;
        String sort = "createdAt";

        assertThat(getAnswerPreviewsService.getAnswerPreviews(userId, sort))
                .hasSize(1);
    }
}
