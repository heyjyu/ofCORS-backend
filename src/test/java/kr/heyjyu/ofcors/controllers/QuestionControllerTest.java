package kr.heyjyu.ofcors.controllers;

import kr.heyjyu.ofcors.application.GetTopQuestionsService;
import kr.heyjyu.ofcors.models.Question;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.*;
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
    private GetTopQuestionsService getTopQuestionsService;

    @Test
    void topQuestions() throws Exception {
        Question question = mock(Question.class);
        given(getTopQuestionsService.getTopQuestions())
                .willReturn(List.of(question));

        mockMvc.perform(MockMvcRequestBuilders.get("/questions?filter=top"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"questions\":[")
                ));
    }
}
