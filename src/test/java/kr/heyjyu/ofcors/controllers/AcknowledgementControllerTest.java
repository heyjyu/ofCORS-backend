package kr.heyjyu.ofcors.controllers;

import kr.heyjyu.ofcors.application.CreateAcknowledgementService;
import kr.heyjyu.ofcors.models.Acknowledgement;
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

@WebMvcTest(AcknowledgementController.class)
@ActiveProfiles("test")
class AcknowledgementControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateAcknowledgementService createAcknowledgementService;

    @SpyBean
    private JwtUtil jwtUtil;

    private String token;

    @BeforeEach
    void setup() {
        token = jwtUtil.encode(1L);
    }

    @Test
    void create() throws Exception {
        given(createAcknowledgementService.create(any(), any(), any(), any(), any()))
                .willReturn(Acknowledgement.fake());

        mockMvc.perform(MockMvcRequestBuilders.post("/acknowledgements")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"questionId\":1," +
                        "\"answerId\":1," +
                        "\"message\":\"감사합니다\"," +
                        "\"points\":30" +
                        "}"))
                .andExpect(status().isCreated());
    }
}
