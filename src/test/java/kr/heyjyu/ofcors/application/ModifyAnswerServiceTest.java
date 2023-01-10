package kr.heyjyu.ofcors.application;

import kr.heyjyu.ofcors.exceptions.EmptyBody;
import kr.heyjyu.ofcors.exceptions.InvalidUser;
import kr.heyjyu.ofcors.exceptions.UnmodifiableAnswer;
import kr.heyjyu.ofcors.models.Answer;
import kr.heyjyu.ofcors.models.AnswerId;
import kr.heyjyu.ofcors.models.Body;
import kr.heyjyu.ofcors.models.Question;
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

class ModifyAnswerServiceTest {
    private AnswerRepository answerRepository;
    private ModifyAnswerService modifyAnswerService;
    private QuestionRepository questionRepository;

    @BeforeEach
    void setup() {
        answerRepository = mock(AnswerRepository.class);
        questionRepository = mock(QuestionRepository.class);
        modifyAnswerService = new ModifyAnswerService(answerRepository, questionRepository);
    }

    @Test
    void modify() {
        Answer answer = Answer.fake();

        given(answerRepository.findById(any()))
                .willReturn(Optional.of(answer));
        given(questionRepository.findById(any()))
                .willReturn(Optional.of(Question.fake()));

        Long userId = 3L;
        AnswerId answerId = new AnswerId(1L);
        Body body = new Body("수정한 답변입니다");

        modifyAnswerService.modify(userId, answerId, body);

        assertThat(answer.getBody()).isEqualTo(body);
    }

    @Test
    void modifyQuestionNotMine() {
        given(answerRepository.findById(any()))
                .willReturn(Optional.of(Answer.fake()));
        given(questionRepository.findById(any()))
                .willReturn(Optional.of(Question.fake()));

        Long userId = 2L;
        AnswerId answerId = new AnswerId(1L);
        Body body = new Body("수정한 답변입니다");

        assertThrows(InvalidUser.class, () -> modifyAnswerService.modify(userId, answerId, body));
    }

    @Test
    void modifyAnswerOfClosedQuestion() {
        given(answerRepository.findById(any()))
                .willReturn(Optional.of(Answer.fake()));

        Long userId = 3L;
        AnswerId answerId = new AnswerId(1L);
        Body body = new Body("수정한 답변입니다");

        Question question = Question.fake();
        question.adopt(1L, answerId);

        given(questionRepository.findById(any()))
                .willReturn(Optional.of(question));

        assertThrows(UnmodifiableAnswer.class, () -> modifyAnswerService.modify(userId, answerId, body));
    }
}
