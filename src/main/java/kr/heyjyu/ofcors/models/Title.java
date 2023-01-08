package kr.heyjyu.ofcors.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kr.heyjyu.ofcors.exceptions.EmptyTitle;

import java.util.Objects;

@Embeddable
public class Title {
    @Column(name = "title")
    private String value;

    public Title() {
    }

    public Title(String value) {
        if (value == null || value.equals("")) {
            throw new EmptyTitle();
        }

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

        Title otherTitle = (Title) other;

        return Objects.equals(value, otherTitle.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
