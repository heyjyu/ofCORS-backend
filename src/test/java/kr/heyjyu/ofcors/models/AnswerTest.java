package kr.heyjyu.ofcors.models;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AnswerTest {
    @Test
    void toggleLike() {
        Answer answer = Answer.fake();
        LikeUserId likeUserId = new LikeUserId(1L);

        answer.toggleLike(likeUserId);

        assertThat(answer.getLikeUserIds()).hasSize(1);

        answer.toggleLike(likeUserId);

        assertThat(answer.getLikeUserIds()).hasSize(0);
    }
}
