package kr.heyjyu.ofcors.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kr.heyjyu.ofcors.dtos.LikeUserIdDto;

import java.util.Objects;

@Embeddable
public class LikeUserId {
    @Column(name = "likeUserId")
    private Long value;

    public LikeUserId() {
    }

    public LikeUserId(Long value) {
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

        LikeUserId otherLikeUserId = (LikeUserId) other;

        return Objects.equals(value, otherLikeUserId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public LikeUserIdDto toDto() {
        return new LikeUserIdDto(value);
    }
}
