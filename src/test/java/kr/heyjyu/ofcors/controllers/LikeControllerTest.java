package kr.heyjyu.ofcors.controllers;

import kr.heyjyu.ofcors.application.ToggleAnswerLikeService;
import kr.heyjyu.ofcors.application.ToggleQuestionLikeService;
import kr.heyjyu.ofcors.dtos.LikeUserIdDto;
import kr.heyjyu.ofcors.dtos.LikeUserIdsDto;
import kr.heyjyu.ofcors.models.LikeUserId;
import kr.heyjyu.ofcors.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Set;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LikeController.class)
@ActiveProfiles("test")
class LikeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ToggleQuestionLikeService toggleQuestionLikeService;

    @MockBean
    private ToggleAnswerLikeService toggleAnswerLikeService;

    @SpyBean
    private JwtUtil jwtUtil;

    private String token;

    @BeforeEach
    void setup() {
        token = jwtUtil.encode(1L);
    }

    @Test
    void toggleQuestionLike() throws Exception {
        given(toggleQuestionLikeService.toggleLike(any(), any()))
                .willReturn(LikeUserIdsDto.fake());

        mockMvc.perform(MockMvcRequestBuilders.patch("/questions/1/likeUserIds")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"likeUserIds\":[")
                ));
    }

    @Test
    void toggleAnswerLike() throws Exception {
        given(toggleAnswerLikeService.toggleLike(any(), any()))
                .willReturn(LikeUserIdsDto.fake());

        mockMvc.perform(MockMvcRequestBuilders.patch("/answers/1/likeUserIds")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"likeUserIds\":[")
                ));
    }
}
