package kr.heyjyu.ofcors.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class ReceiverId {
    @Column(name = "receiverId")
    private Long value;

    public ReceiverId() {
    }

    public ReceiverId(Long value) {
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

        ReceiverId otherAuthorId = (ReceiverId) other;

        return Objects.equals(value, otherAuthorId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
