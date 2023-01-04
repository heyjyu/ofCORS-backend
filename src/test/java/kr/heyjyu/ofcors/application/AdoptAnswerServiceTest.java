package kr.heyjyu.ofcors.application;

import kr.heyjyu.ofcors.exceptions.AlreadyAdopted;
import kr.heyjyu.ofcors.exceptions.InvalidUser;
import kr.heyjyu.ofcors.models.AnswerId;
import kr.heyjyu.ofcors.models.Question;
import kr.heyjyu.ofcors.models.QuestionId;
import kr.heyjyu.ofcors.repositories.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class AdoptAnswerServiceTest {
    private QuestionRepository questionRepository;
    private AdoptAnswerService adoptAnswerService;

    @BeforeEach
    void setup() {
        questionRepository = mock(QuestionRepository.class);
        adoptAnswerService = new AdoptAnswerService(questionRepository);
    }

    @Test
    void adopt() {
        given(questionRepository.findById(any()))
                .willReturn(Optional.of(Question.fake()));

        Long userId = 1L;
        QuestionId questionId = new QuestionId(1L);
        AnswerId answerId = new AnswerId(1L);

        assertDoesNotThrow(() -> adoptAnswerService.adopt(userId, questionId, answerId));
    }

    @Test
    void adoptQuestionOfOtherPerson() {
        given(questionRepository.findById(any()))
                .willReturn(Optional.of(Question.fake()));

        Long userId = 2L;
        QuestionId questionId = new QuestionId(1L);
        AnswerId answerId = new AnswerId(1L);

        assertThrows(InvalidUser.class, () -> adoptAnswerService.adopt(userId, questionId, answerId));
    }

    @Test
    void adoptWhenAlreadyAdopted() {
        Long userId = 1L;
        QuestionId questionId = new QuestionId(1L);
        AnswerId answerId = new AnswerId(1L);

        Question question = Question.fake();

        question.adopt(userId, answerId);

        given(questionRepository.findById(any()))
                .willReturn(Optional.of(question));

        assertThrows(AlreadyAdopted.class, () -> adoptAnswerService.adopt(userId, questionId, answerId));
    }
}
