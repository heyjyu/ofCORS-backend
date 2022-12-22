package kr.heyjyu.ofcors.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class AuthorId {
    @Column(name = "authorId")
    private Long value;

    public AuthorId() {
    }

    public AuthorId(Long value) {
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

        AuthorId otherAuthorId = (AuthorId) other;

        return Objects.equals(value, otherAuthorId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
