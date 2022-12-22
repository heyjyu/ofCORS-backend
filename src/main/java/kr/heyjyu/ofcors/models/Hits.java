package kr.heyjyu.ofcors.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Hits {
    @Column(name = "hits")
    private Long value;

    public Hits() {
    }

    public Hits(Long value) {
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

        Hits otherHits = (Hits) other;

        return Objects.equals(value, otherHits.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
