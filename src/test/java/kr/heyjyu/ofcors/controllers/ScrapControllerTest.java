package kr.heyjyu.ofcors.controllers;

import kr.heyjyu.ofcors.application.CancelScrapQuestionService;
import kr.heyjyu.ofcors.application.ScrapQuestionService;
import kr.heyjyu.ofcors.models.Question;
import kr.heyjyu.ofcors.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ScrapController.class)
class ScrapControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ScrapQuestionService scrapQuestionService;

    @MockBean
    private CancelScrapQuestionService cancelScrapQuestionService;

    @SpyBean
    private JwtUtil jwtUtil;

    private String token;

    @BeforeEach
    void setup() {
        token = jwtUtil.encode(1L);
    }

    @Test
    void scrap() throws Exception {
        given(scrapQuestionService.scrap(any(), any()))
                .willReturn(Question.fake());

        mockMvc.perform(MockMvcRequestBuilders.patch("/questions/1/scrapUserIds")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"scrapped\":true}"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("scrapUserIds")
                ));
    }

    @Test
    void cancelScrap() throws Exception {
        given(cancelScrapQuestionService.cancelScrap(any(), any()))
                .willReturn(Question.fake());

        mockMvc.perform(MockMvcRequestBuilders.patch("/questions/1/scrapUserIds")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"scrapped\":false}"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("scrapUserIds")
                ));
    }
}
