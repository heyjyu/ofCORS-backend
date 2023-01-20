package kr.heyjyu.ofcors.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Quantity {
    @Column(name = "quantity")
    private Long value;

    public Quantity() {
    }

    public Quantity(Long value) {
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

        Quantity otherQuantity = (Quantity) other;

        return Objects.equals(value, otherQuantity.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
