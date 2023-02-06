package kr.heyjyu.ofcors.controllers;

import kr.heyjyu.ofcors.application.VerifyAccountService;
import kr.heyjyu.ofcors.dtos.AccountVerificationResultDto;
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

@WebMvcTest(VerificationController.class)
class VerificationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VerifyAccountService verifyAccountService;

    @SpyBean
    private JwtUtil jwtUtil;

    private String token;

    @BeforeEach
    void setup() {
        token = jwtUtil.encode(1L);
    }

    @Test
    void verifyAccount() throws Exception {
        given(verifyAccountService.verify(any(), any(), any()))
                .willReturn(new AccountVerificationResultDto(true));

        mockMvc.perform(MockMvcRequestBuilders.post("/account/verify")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"bank\":\"하나은행\"," +
                                "\"accountNumber\":\"11111111\"" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("validated")
                ));
    }
}
