package kr.heyjyu.ofcors.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class FollowerId {
    @Column(name = "followerId")
    private Long value;

    public FollowerId() {
    }

    public FollowerId(Long value) {
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

        FollowerId otherFollowerId = (FollowerId) other;

        return Objects.equals(value, otherFollowerId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
