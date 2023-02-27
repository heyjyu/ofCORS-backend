package kr.heyjyu.ofcors.models;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HitsTest {
    @Test
    void countUp() {
        Hits hits = new Hits(1L);

        assertThat(hits.countUp()).isEqualTo(new Hits(2L));
    }
}
