package kr.heyjyu.ofcors.controllers;

import kr.heyjyu.ofcors.application.LoginService;
import kr.heyjyu.ofcors.application.TrialLoginService;
import kr.heyjyu.ofcors.exceptions.LoginFailed;
import kr.heyjyu.ofcors.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SessionController.class)
@ActiveProfiles("test")
class SessionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginService loginService;

    @MockBean
    private TrialLoginService trialLoginService;

    @Test
    void loginSuccess() throws Exception {
        given(loginService.login(any(), any()))
                .willReturn(User.fake());

        mockMvc.perform(MockMvcRequestBuilders.post("/session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"email\":\"test@example.com\"," +
                                "\"password\":\"Abcdef1!\"" +
                                "}"))
                .andExpect(status().isCreated())
                .andExpect(content().string(
                        containsString("accessToken")
                ));
    }

    @Test
    void loginFailed() throws Exception {
        given(loginService.login(any(), any()))
                .willThrow(new LoginFailed());

        mockMvc.perform(MockMvcRequestBuilders.post("/session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"email\":\"wrong@example.com\"," +
                                "\"password\":\"Abcdef1!\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void trialLogin() throws Exception {
        given(trialLoginService.login())
                .willReturn(User.fake());

        mockMvc.perform(MockMvcRequestBuilders.post("/session/trial"))
                .andExpect(status().isCreated())
                .andExpect(content().string(
                        containsString("accessToken")
                ));
    }
}
