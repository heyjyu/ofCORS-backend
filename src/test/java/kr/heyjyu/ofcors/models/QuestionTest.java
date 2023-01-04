package kr.heyjyu.ofcors.models;

import kr.heyjyu.ofcors.exceptions.AlreadyAdopted;
import kr.heyjyu.ofcors.exceptions.InvalidUser;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class QuestionTest {
    @Test
    void adopt() {
        Question question = Question.fake();

        Long userId = 1L;
        AnswerId answerId = new AnswerId(1L);

        question.adopt(userId, answerId);

        assertThat(question.getStatus())
                .isEqualTo(QuestionStatus.CLOSED);

        assertThat(question.getSelectedAnswerId())
                .isEqualTo(answerId);
    }

    @Test
    void adoptAlreadyAdoptedQuestion() {
        Question question = Question.fake();

        Long userId = 1L;
        AnswerId answerId = new AnswerId(1L);

        question.adopt(userId, answerId);

        assertThrows(AlreadyAdopted.class, () -> question.adopt(userId, answerId));
    }

    @Test
    void adoptQuestionOfOthers() {
        Question question = Question.fake();

        Long userId = 2L;
        AnswerId answerId = new AnswerId(1L);

        assertThrows(InvalidUser.class, () -> question.adopt(userId, answerId));
    }
}
