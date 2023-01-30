package kr.heyjyu.ofcors.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kr.heyjyu.ofcors.dtos.LikeUserIdDto;
import kr.heyjyu.ofcors.dtos.ScrapUserIdDto;

import java.util.Objects;

@Embeddable
public class ScrapUserId {
    @Column(name = "scrapUserId")
    private Long value;

    public ScrapUserId() {
    }

    public ScrapUserId(Long value) {
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

        ScrapUserId otherScrapUserId = (ScrapUserId) other;

        return Objects.equals(value, otherScrapUserId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public ScrapUserIdDto toDto() {
        return new ScrapUserIdDto(value);
    }
}
