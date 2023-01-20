package kr.heyjyu.ofcors.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Price {
    @Column(name = "price")
    private Long value;

    public Price() {
    }

    public Price(Long value) {
        this.value = value;
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

        Price otherPrice = (Price) other;

        return Objects.equals(value, otherPrice.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
