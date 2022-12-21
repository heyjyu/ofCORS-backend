package kr.heyjyu.ofcors.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Points {
    @Column(name = "points")
    private Long value;

    public Points() {
    }

    public Points(Long value) {
        this.value = value;
    }

    public Long value() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        Points otherPoints = (Points) other;

        return Objects.equals(value, otherPoints.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
