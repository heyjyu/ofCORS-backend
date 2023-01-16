package kr.heyjyu.ofcors.controllers;

import kr.heyjyu.ofcors.application.GetQuestionPreviewsService;
import kr.heyjyu.ofcors.dtos.QuestionPreviewDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QuestionPreviewController.class)
@ActiveProfiles("test")
class QuestionPreviewControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetQuestionPreviewsService getQuestionPreviewsService;

    @Test
    void list() throws Exception {
        given(getQuestionPreviewsService.getQuestionPreviews(any()))
                .willReturn(List.of(QuestionPreviewDto.fake()));

        mockMvc.perform(MockMvcRequestBuilders.get("/question-previews?userId=1"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"questionPreviews\":[")
                ));
    }
}
