package kr.heyjyu.ofcors.application;

import kr.heyjyu.ofcors.models.Question;
import kr.heyjyu.ofcors.repositories.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class GetQuestionsServiceTest {
    private QuestionRepository questionRepository;
    private GetQuestionsService getQuestionsService;

    @BeforeEach
    void setup() {
        questionRepository = mock(QuestionRepository.class);
        getQuestionsService = new GetQuestionsService(questionRepository);
    }

    @Test
    void getTopQuestions() {
        Question question = mock(Question.class);

        given(questionRepository.findAll(any(Specification.class), any(Pageable.class)))
                .willReturn(new PageImpl<>(List.of(question)));

        String sort = "like";
        String period = "week";
        String status = "open";
        Integer count = 30;

        assertThat(getQuestionsService.getQuestions(sort, period, status, count))
                .hasSize(1);
    }
}
