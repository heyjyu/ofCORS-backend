package kr.heyjyu.ofcors.controllers;

import kr.heyjyu.ofcors.application.CreateAnswerService;
import kr.heyjyu.ofcors.application.GetAnswerService;
import kr.heyjyu.ofcors.application.GetAnswersService;
import kr.heyjyu.ofcors.dtos.AnswerDto;
import kr.heyjyu.ofcors.dtos.QuestionDto;
import kr.heyjyu.ofcors.models.Answer;
import kr.heyjyu.ofcors.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
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

@WebMvcTest(AnswerController.class)
@ActiveProfiles("test")
class AnswerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateAnswerService createAnswerService;

    @MockBean
    private GetAnswersService getAnswersService;

    @MockBean
    private GetAnswerService getAnswerService;

    @SpyBean
    private JwtUtil jwtUtil;

    private String token;

    @BeforeEach
    void setup() {
        token = jwtUtil.encode(1L);
    }

    @Test
    void createWithToken() throws Exception {
        given(createAnswerService.create(any(), any(), any()))
                .willReturn(Answer.fake());

        mockMvc.perform(MockMvcRequestBuilders.post("/answers")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"questionId\":1," +
                        "\"body\":\"헤더를 추가해보세요.\"" +
                        "}"))
                .andExpect(status().isCreated());
    }

    @Test
    void createWithoutToken() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/answers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"questionId\":1," +
                                "\"body\":\"헤더를 추가해보세요.\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createWithWrongToken() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/answers")
                        .header("Authorization", "Bearer WRONG.TOKEN")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"questionId\":1," +
                                "\"body\":\"헤더를 추가해보세요.\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void list() throws Exception {
        given(getAnswersService.getAnswers(any()))
                .willReturn(List.of(AnswerDto.fake()));

        mockMvc.perform(MockMvcRequestBuilders.get("/answers?questionId=1"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"answers\":")
                ));
    }

    @Test
    void detail() throws Exception {
        given(getAnswerService.getAnswer(any()))
                .willReturn(AnswerDto.fake());

        mockMvc.perform(MockMvcRequestBuilders.get("/answers/1"))
                .andExpect(status().isOk());
    }
}
