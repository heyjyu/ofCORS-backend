package kr.heyjyu.ofcors.controllers;

import kr.heyjyu.ofcors.application.CountUserService;
import kr.heyjyu.ofcors.application.CreateUserService;
import kr.heyjyu.ofcors.application.GetUserService;
import kr.heyjyu.ofcors.application.GetUsersService;
import kr.heyjyu.ofcors.dtos.UserDto;
import kr.heyjyu.ofcors.exceptions.ExistingEmail;
import kr.heyjyu.ofcors.models.Email;
import kr.heyjyu.ofcors.models.User;
import kr.heyjyu.ofcors.utils.JwtUtil;
import org.apache.catalina.Group;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ActiveProfiles("test")
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetUserService getUserService;

    @MockBean
    private CreateUserService createUserService;

    @MockBean
    private CountUserService countUserService;

    @MockBean
    private GetUsersService getUsersService;

    @SpyBean
    private JwtUtil jwtUtil;

    private String token;

    UserControllerTest() {
    }

    @BeforeEach
    void setup() {
        token = jwtUtil.encode(1L);
    }

    @Test
    void userCountWithExistingEmail() throws Exception {
        given(countUserService.count(any()))
                .willReturn(1);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/count?email=exist@email.com"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"count\":1")
                ));
    }

    @Test
    void list() throws Exception {
        given(getUsersService.getUsers(any(), any(), any()))
                .willReturn(List.of(UserDto.fake()));

        mockMvc.perform(MockMvcRequestBuilders.get("/users?sort=like&keyword=web"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"users\":[")
                ));
    }

    @Test
    void user() throws Exception {
        given(getUserService.getUser(any()))
                .willReturn(User.fake());

        mockMvc.perform(MockMvcRequestBuilders.get("/users/me")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"points\"")
                ));
    }

    @Test
    void userWithWrongToken() throws Exception {
        given(getUserService.getUser(any()))
                .willReturn(User.fake());

        mockMvc.perform(MockMvcRequestBuilders.get("/users/me")
                        .header("Authorization", "Bearer WRONG.TOKEN"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void signUp() throws Exception {
        given(createUserService.create(any(), any(), any()))
                .willReturn(User.fake());

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"displayName\":\"joo\"," +
                                "\"email\":\"test@example.com\"," +
                                "\"password\":\"Abcdef1!\"" +
                                "}"))
                .andExpect(status().isCreated())
                .andExpect(content().string(
                        containsString("\"id\"")
                ));
    }

    @Test
    void signUpWithShortDisplayName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"displayName\":\"jo\"," +
                                "\"email\":\"test@example.com\"," +
                                "\"password\":\"Abcdef1!\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void signUpWithWrongEmailFormat() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"displayName\":\"joo\"," +
                                "\"email\":\"wrongEmail\"," +
                                "\"password\":\"Abcdef1!\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void signUpWithExistingEmail() throws Exception {
        given(createUserService.create(any(), any(), any()))
                .willThrow(new ExistingEmail(new Email("exist@email.com")));

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"displayName\":\"joo\"," +
                                "\"email\":\"exist@email.com\"," +
                                "\"password\":\"Abcdef1!\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void signUpWithShortPassword() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"displayName\":\"joo\"," +
                                "\"email\":\"exist@email.com\"," +
                                "\"password\":\"Abcde1!\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void signUpWithPasswordWithoutUppercase() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"displayName\":\"joo\"," +
                                "\"email\":\"exist@email.com\"," +
                                "\"password\":\"abcdef1!\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void signUpWithPasswordWithoutLowercase() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"displayName\":\"joo\"," +
                                "\"email\":\"exist@email.com\"," +
                                "\"password\":\"ABCDEF1!\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void signUpWithPasswordWithoutNumber() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"displayName\":\"joo\"," +
                                "\"email\":\"exist@email.com\"," +
                                "\"password\":\"Abcdefg!\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void signUpWithPasswordWithoutSpecialCharacter() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"displayName\":\"joo\"," +
                                "\"email\":\"exist@email.com\"," +
                                "\"password\":\"Abcdefg1\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }
}
