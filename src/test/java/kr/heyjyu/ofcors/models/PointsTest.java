package kr.heyjyu.ofcors.models;

import kr.heyjyu.ofcors.exceptions.NotEnoughPoints;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PointsTest {
    @Test
    void deduct() {
        Points points = new Points(20L);

        assertThat(points.deduct(new Points(10L)))
                .isEqualTo(new Points(10L));

        assertThrows(NotEnoughPoints.class, () -> points.deduct(new Points(100L)));
    }
}
