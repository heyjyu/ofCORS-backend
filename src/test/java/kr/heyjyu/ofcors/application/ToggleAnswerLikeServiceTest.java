package kr.heyjyu.ofcors.application;

import kr.heyjyu.ofcors.models.Answer;
import kr.heyjyu.ofcors.models.AnswerId;
import kr.heyjyu.ofcors.models.LikeUserId;
import kr.heyjyu.ofcors.models.Question;
import kr.heyjyu.ofcors.models.QuestionId;
import kr.heyjyu.ofcors.repositories.AnswerRepository;
import kr.heyjyu.ofcors.repositories.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ToggleAnswerLikeServiceTest {
    private AnswerRepository answerRepository;
    private ToggleAnswerLikeService toggleAnswerLikeService;

    @BeforeEach
    void setup() {
        answerRepository = mock(AnswerRepository.class);
        toggleAnswerLikeService = new ToggleAnswerLikeService(answerRepository);
    }

    @Test
    void toggleLike() {
        Answer answer = Answer.fake();

        given(answerRepository.findById(any()))
                .willReturn(Optional.of(answer));

        AnswerId answerId = new AnswerId(1L);
        LikeUserId likeUserId = new LikeUserId(1L);

        toggleAnswerLikeService.toggleLike(answerId, likeUserId);

        assertThat(answer.getLikeUserIds()).hasSize(1);

        toggleAnswerLikeService.toggleLike(answerId, likeUserId);

        assertThat(answer.getLikeUserIds()).hasSize(0);
    }

}
