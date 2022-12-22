package kr.heyjyu.ofcors.application;

import kr.heyjyu.ofcors.models.Question;
import kr.heyjyu.ofcors.repositories.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class GetTopQuestionsServiceTest {
    private QuestionRepository questionRepository;
    private GetTopQuestionsService getTopQuestionsService;

    @BeforeEach
    void setup() {
        questionRepository = mock(QuestionRepository.class);
        getTopQuestionsService = new GetTopQuestionsService(questionRepository);
    }

    @Test
    void getTopQuestions() {
        Question question = mock(Question.class);

        given(questionRepository.findAllOrderByLikesCount())
                .willReturn(List.of(question));

        assertThat(getTopQuestionsService.getTopQuestions())
                .hasSize(1);
    }
}
