package kr.heyjyu.ofcors.controllers;

import kr.heyjyu.ofcors.application.GetQuestionsService;
import kr.heyjyu.ofcors.models.Question;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QuestionController.class)
@ActiveProfiles("test")
class QuestionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetQuestionsService getQuestionsService;

    @Test
    void topQuestions() throws Exception {
        Question question = mock(Question.class);
        given(getQuestionsService.getQuestions(any(), any(), any(), any(), any()))
                .willReturn(new PageImpl<>(List.of(question)));

        mockMvc.perform(MockMvcRequestBuilders.get("/questions?sort=like&status=open&period=week&size=30"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"questions\":[")
                ));
    }

    @Test
    void search() throws Exception {
        Question question = mock(Question.class);
        given(getQuestionsService.getQuestions(any(), any(), any(), any(), any()))
                .willReturn(new PageImpl<>(List.of(question)));

        mockMvc.perform(MockMvcRequestBuilders.get("/questions?sort=like&status=closed&keyword=CORS&size=30"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"questions\":[")
                ));
    }

    @Test
    void list() throws Exception {
        Question question = mock(Question.class);
        given(getQuestionsService.getQuestions(any(), any(), any(), any(), any()))
                .willReturn(new PageImpl<>(List.of(question)));

        mockMvc.perform(MockMvcRequestBuilders.get("/questions?sort=points&status=open&size=30"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"questions\":[")
                ));
    }
}
