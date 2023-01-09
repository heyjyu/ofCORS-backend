package kr.heyjyu.ofcors.application;

import kr.heyjyu.ofcors.exceptions.InvalidUser;
import kr.heyjyu.ofcors.exceptions.UnmodifiableQuestion;
import kr.heyjyu.ofcors.models.Answer;
import kr.heyjyu.ofcors.models.Question;
import kr.heyjyu.ofcors.models.QuestionId;
import kr.heyjyu.ofcors.repositories.AnswerRepository;
import kr.heyjyu.ofcors.repositories.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class DeleteQuestionServiceTest {
    private QuestionRepository questionRepository;
    private AnswerRepository answerRepository;
    private DeleteQuestionService deleteQuestionService;

    @BeforeEach
    void setup() {
        questionRepository = mock(QuestionRepository.class);
        answerRepository = mock(AnswerRepository.class);
        deleteQuestionService = new DeleteQuestionService(questionRepository, answerRepository);
    }

    @Test
    void delete() {
        given(questionRepository.findById(any()))
                .willReturn(Optional.of(Question.fake()));

        Long userId = 1L;
        QuestionId questionId = new QuestionId(1L);

        assertDoesNotThrow(() -> deleteQuestionService.delete(userId, questionId));

        verify(questionRepository).delete(any(Question.class));
    }

    @Test
    void deleteQuestionByNotAnAuthor() {
        given(questionRepository.findById(any()))
                .willReturn(Optional.of(Question.fake()));

        Long userId = 2L;
        QuestionId questionId = new QuestionId(1L);

        assertThrows(InvalidUser.class,() -> deleteQuestionService.delete(userId, questionId));
    }

    @Test
    void deleteQuestionWithAnswer() {
        given(questionRepository.findById(any()))
                .willReturn(Optional.of(Question.fake()));

        Long userId = 1L;
        QuestionId questionId = new QuestionId(1L);

        given(answerRepository.findAllByQuestionId(questionId))
                .willReturn(List.of(Answer.fake()));

        assertThrows(UnmodifiableQuestion.class,() -> deleteQuestionService.delete(userId, questionId));
    }
}
