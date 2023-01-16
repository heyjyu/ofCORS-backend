package kr.heyjyu.ofcors.controllers;

import kr.heyjyu.ofcors.application.CreateAnswerService;
import kr.heyjyu.ofcors.application.GetAnswerPreviewsService;
import kr.heyjyu.ofcors.application.GetAnswerService;
import kr.heyjyu.ofcors.application.GetAnswersService;
import kr.heyjyu.ofcors.application.ModifyAnswerService;
import kr.heyjyu.ofcors.dtos.AnswerDto;
import kr.heyjyu.ofcors.dtos.AnswerPreviewDto;
import kr.heyjyu.ofcors.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AnswerPreviewController.class)
@ActiveProfiles("test")
class AnswerPreviewControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetAnswerPreviewsService getAnswerPreviewsService;

    @Test
    void list() throws Exception {
        given(getAnswerPreviewsService.getAnswerPreviews(any()))
                .willReturn(List.of(AnswerPreviewDto.fake()));

        mockMvc.perform(MockMvcRequestBuilders.get("/answer-previews?userId=1"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"answerPreviews\":")
                ));
    }
}
