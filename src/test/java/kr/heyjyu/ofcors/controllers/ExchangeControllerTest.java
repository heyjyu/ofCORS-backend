package kr.heyjyu.ofcors.controllers;

import kr.heyjyu.ofcors.application.ExchangeService;
import kr.heyjyu.ofcors.application.GetExchangesService;
import kr.heyjyu.ofcors.dtos.ExchangeDto;
import kr.heyjyu.ofcors.models.Exchange;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExchangeController.class)
class ExchangeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExchangeService exchangeService;

    @MockBean
    private GetExchangesService getExchangesService;

    @SpyBean
    private JwtUtil jwtUtil;

    private String token;

    @BeforeEach
    void setup() {
        token = jwtUtil.encode(1L);
    }

    @Test
    void exchange() throws Exception {
        given(exchangeService.exchange(any(), any(), any(), any()))
                .willReturn(Exchange.fake());

        mockMvc.perform(MockMvcRequestBuilders.post("/exchanges")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"points\":500," +
                                "\"bank\":\"하나은행\"," +
                                "\"accountNumber\":\"11111111\"" +
                                "}"))
                .andExpect(status().isCreated())
                .andExpect(content().string(
                        containsString("id")
                ));
    }

    @Test
    void list() throws Exception {
        given(getExchangesService.getExchanges(any()))
                .willReturn(List.of(ExchangeDto.fake()));

        mockMvc.perform(MockMvcRequestBuilders.get("/exchanges")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("exchanges")
                ));
    }
}
