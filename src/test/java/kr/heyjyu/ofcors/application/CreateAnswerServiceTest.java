package kr.heyjyu.ofcors.application;

import kr.heyjyu.ofcors.exceptions.AnswerByAuthor;
import kr.heyjyu.ofcors.models.Answer;
import kr.heyjyu.ofcors.models.Body;
import kr.heyjyu.ofcors.models.Question;
import kr.heyjyu.ofcors.models.QuestionId;
import kr.heyjyu.ofcors.repositories.AnswerRepository;
import kr.heyjyu.ofcors.repositories.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CreateAnswerServiceTest {
    private AnswerRepository answerRepository;
    private QuestionRepository questionRepository;
    private CreateAnswerService createAnswerService;

    @BeforeEach
    void setup() {
        answerRepository = mock(AnswerRepository.class);
        questionRepository = mock(QuestionRepository.class);
        createAnswerService = new CreateAnswerService(answerRepository, questionRepository);
    }

    @Test
    void create() {
        given(questionRepository.findById(1L))
                .willReturn(Optional.of(Question.fake()));

        Long userId = 2L;
        QuestionId questionId = new QuestionId(1L);
        Body body = new Body("헤더를 붙여보세요");

        Answer answer = createAnswerService.create(userId, questionId, body);

        assertThat(answer).isNotNull();
        verify(answerRepository).save(any(Answer.class));
    }

    @Test
    void createByQuestionAuthor() {
        given(questionRepository.findById(1L))
                .willReturn(Optional.of(Question.fake()));

        Long userId = 1L;
        QuestionId questionId = new QuestionId(1L);
        Body body = new Body("헤더를 붙여보세요");

        assertThrows(AnswerByAuthor.class, () -> createAnswerService.create(userId, questionId, body));
    }
}
