package kr.heyjyu.ofcors.controllers;

import kr.heyjyu.ofcors.application.ChargeService;
import kr.heyjyu.ofcors.models.KakaoPayApproval;
import kr.heyjyu.ofcors.utils.JwtUtil;
import kr.heyjyu.ofcors.utils.KakaoPay;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ChargeController.class)
@ActiveProfiles("test")
class ChargeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChargeService chargeService;

    @MockBean
    private KakaoPay kakaoPay;

    @SpyBean
    private JwtUtil jwtUtil;

    private String token;

    @BeforeEach
    void setup() {
        token = jwtUtil.encode(1L);
    }

    @Test
    void charge() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/charges")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"quantity\":1" +
                        "}"))
                .andExpect(status().isOk());
    }

    @Test
    void result() throws Exception {
        given(chargeService.completeCharge(any()))
                .willReturn(new KakaoPayApproval());

        mockMvc.perform(MockMvcRequestBuilders.get("/charges/kakaoPaySuccess?pg_token=pg_token"))
                .andExpect(status().isOk());
    }
}
