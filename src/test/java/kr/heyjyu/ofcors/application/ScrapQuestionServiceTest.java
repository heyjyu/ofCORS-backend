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

class ScrapQuestionServiceTest {
    private QuestionRepository questionRepository;
    private ScrapQuestionService scrapQuestionService;

    @BeforeEach
    void setup() {
        questionRepository = mock(QuestionRepository.class);
        scrapQuestionService = new ScrapQuestionService(questionRepository);
    }

    @Test
    void scrap() {
        Question question = Question.fake();

        given(questionRepository.findById(1L))
                .willReturn(Optional.of(question));

        Long userId = 1L;
        QuestionId questionId = new QuestionId(1L);

        scrapQuestionService.scrap(userId, questionId);

        assertThat(question.getScrapUserIds()).contains(new ScrapUserId(1L));
    }
}
