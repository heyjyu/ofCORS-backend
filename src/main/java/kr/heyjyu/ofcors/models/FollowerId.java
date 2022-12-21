package kr.heyjyu.ofcors.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class FollowerId {
    @Column(name = "followerId")
    private String value;

    public FollowerId() {
    }

    public FollowerId(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        FollowerId otherFollowerId = (FollowerId) other;

        return Objects.equals(value, otherFollowerId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
