package kr.heyjyu.ofcors.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kr.heyjyu.ofcors.exceptions.NotEnoughPoints;
import org.springframework.security.core.parameters.P;

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

    public Points(Quantity quantity) {
        this.value = quantity.value();
    }

    public Long value() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        Points otherPoints = (Points) other;

        return Objects.equals(value, otherPoints.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public Points deduct(Points points) {
        if (this.value < points.value) {
            throw new NotEnoughPoints();
        }

        return new Points(this.value - points.value);
    }

    public Points add(Points points) {
        return new Points(this.value + points.value);
    }

    public boolean isAffordable(Points points) {
        return this.value >= points.value;
    }
}
