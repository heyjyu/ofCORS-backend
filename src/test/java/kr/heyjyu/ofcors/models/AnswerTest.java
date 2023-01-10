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

    @Test
    void isAuthor() {
        Answer answer = Answer.fake();

        assertThat(answer.isAuthor(3L)).isTrue();
        assertThat(answer.isAuthor(2L)).isFalse();
    }

    @Test
    void modify() {
        Answer answer = Answer.fake();

        Body body = new Body("수정된 답변입니다");

        answer.modify(body);

        assertThat(answer.getBody()).isEqualTo(body);
    }
}
