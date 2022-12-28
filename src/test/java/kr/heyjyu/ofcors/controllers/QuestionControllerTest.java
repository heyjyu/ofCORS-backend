package kr.heyjyu.ofcors.controllers;

import kr.heyjyu.ofcors.application.CreateQuestionService;
import kr.heyjyu.ofcors.application.GetQuestionService;
import kr.heyjyu.ofcors.application.GetQuestionsService;
import kr.heyjyu.ofcors.dtos.QuestionDto;
import kr.heyjyu.ofcors.models.Question;
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

@WebMvcTest(QuestionController.class)
@ActiveProfiles("test")
class QuestionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetQuestionsService getQuestionsService;

    @MockBean
    private CreateQuestionService createQuestionService;

    @MockBean
    private GetQuestionService getQuestionService;

    @SpyBean
    private JwtUtil jwtUtil;

    private String token;

    @BeforeEach
    void setup() {
        token = jwtUtil.encode(1L);
    }

    @Test
    void topQuestions() throws Exception {
        Question question = mock(Question.class);
        given(getQuestionsService.getQuestions(any(), any(), any(), any(), any()))
                .willReturn(List.of(QuestionDto.fake()));

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
                .willReturn(List.of(QuestionDto.fake()));

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
                .willReturn(List.of(QuestionDto.fake()));

        mockMvc.perform(MockMvcRequestBuilders.get("/questions?sort=points&status=open&size=30"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"questions\":[")
                ));
    }

    @Test
    void detail() throws Exception {
        given(getQuestionService.getQuestion(any()))
                .willReturn(QuestionDto.fake());

        mockMvc.perform(MockMvcRequestBuilders.get("/questions/1"))
                .andExpect(status().isOk());
    }

    @Test
    void create() throws Exception {
        given(createQuestionService.create(any(), any(), any(), any(), any()))
                .willReturn(Question.fake());

        mockMvc.perform(MockMvcRequestBuilders.post("/questions")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"title\":\"No 'Access-Control-Allow-Origin' 에러가 발생합니다\"," +
                                "\"body\":\"서버 배포 후 CORS 에러가 발생합니다.\"," +
                                "\"tags\":[\"Web\"]," +
                                "\"points\":\"30\"" +
                                "}"))
                .andExpect(status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders.post("/questions")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"title\":\"No 'Access-Control-Allow-Origin' 에러가 발생합니다\"," +
                                "\"body\":\"서버 배포 후 CORS 에러가 발생합니다.\"" +
                                "}"))
                .andExpect(status().isCreated());
    }
}
