package kr.heyjyu.ofcors.application;

import kr.heyjyu.ofcors.models.LikeUserId;
import kr.heyjyu.ofcors.models.Question;
import kr.heyjyu.ofcors.models.QuestionId;
import kr.heyjyu.ofcors.models.User;
import kr.heyjyu.ofcors.repositories.QuestionRepository;
import kr.heyjyu.ofcors.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ToggleQuestionLikeServiceTest {
    private QuestionRepository questionRepository;
    private ToggleQuestionLikeService toggleQuestionLikeService;

    @BeforeEach
    void setup() {
        questionRepository = mock(QuestionRepository.class);
        toggleQuestionLikeService = new ToggleQuestionLikeService(questionRepository);
    }

    @Test
    void toggleLike() {
        Question question = Question.fake();

        given(questionRepository.findById(any()))
                .willReturn(Optional.of(question));

        QuestionId questionId = new QuestionId(1L);
        LikeUserId likeUserId = new LikeUserId(1L);

        toggleQuestionLikeService.toggleLike(questionId, likeUserId);

        assertThat(question.getLikeUserIds()).hasSize(1);

        toggleQuestionLikeService.toggleLike(questionId, likeUserId);

        assertThat(question.getLikeUserIds()).hasSize(0);
    }
}
