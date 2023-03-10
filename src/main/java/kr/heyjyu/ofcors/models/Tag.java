package kr.heyjyu.ofcors.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kr.heyjyu.ofcors.dtos.TagDto;

import java.util.Objects;

@Embeddable
public class Tag {
    @Column(name = "tag")
    private String value;

    public Tag() {
    }

    public Tag(String value) {
        this.value = value;
    }

    public String value() {
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

        Tag otherTag = (Tag) other;

        return Objects.equals(value, otherTag.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public TagDto toDto() {
        return new TagDto(value);
    }
}
