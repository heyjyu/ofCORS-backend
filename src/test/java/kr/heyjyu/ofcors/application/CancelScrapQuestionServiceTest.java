package kr.heyjyu.ofcors.application;

import kr.heyjyu.ofcors.models.Question;
import kr.heyjyu.ofcors.models.QuestionId;
import kr.heyjyu.ofcors.models.ScrapUserId;
import kr.heyjyu.ofcors.repositories.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class CancelScrapQuestionServiceTest {
    private QuestionRepository questionRepository;
    private CancelScrapQuestionService cancelScrapQuestionService;

    @BeforeEach
    void setup() {
        questionRepository = mock(QuestionRepository.class);
        cancelScrapQuestionService = new CancelScrapQuestionService(questionRepository);
    }

    @Test
    void cancelScrap() {
        Question question = Question.fake();

        given(questionRepository.findById(1L))
                .willReturn(Optional.of(question));

        Long userId = 1L;
        QuestionId questionId = new QuestionId(1L);

        cancelScrapQuestionService.cancelScrap(userId, questionId);

        assertThat(question.getScrapUserIds()).doesNotContain(new ScrapUserId(1L));
    }
}
