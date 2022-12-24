package kr.heyjyu.ofcors.models;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QuestionStatusTest {
    @Test
    void of() {
        String status = "open";

        QuestionStatus questionStatus = QuestionStatus.of(status);

        assertThat(questionStatus.value()).isEqualTo(status);
    }
}
