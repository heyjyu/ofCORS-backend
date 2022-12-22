package kr.heyjyu.ofcors.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Name {
    @Column(name = "name")
    private String value;

    public Name() {
    }

    public Name(String value) {
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

        Name otherName = (Name) other;

        return Objects.equals(value, otherName.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
