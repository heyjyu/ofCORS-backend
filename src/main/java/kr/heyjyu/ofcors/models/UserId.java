package kr.heyjyu.ofcors.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class UserId {
    @Column(name = "userId")
    private Long value;

    public UserId() {
    }

    public UserId(Long value) {
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

        UserId otherUserId = (UserId) other;

        return Objects.equals(value, otherUserId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
