package kr.heyjyu.ofcors.controllers;

import kr.heyjyu.ofcors.application.CreateAnswerService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AnswerController.class)
@ActiveProfiles("test")
class AnswerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateAnswerService createAnswerService;

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
}
