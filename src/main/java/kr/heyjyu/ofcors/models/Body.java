package kr.heyjyu.ofcors.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kr.heyjyu.ofcors.exceptions.EmptyBody;

import java.util.Objects;

@Embeddable
public class Body {
    @Column(name = "body")
    private String value;

    public Body() {
    }

    public Body(String value) {
        if (value == null || value.equals("")) {
            throw new EmptyBody();
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

        Body otherBody = (Body) other;

        return Objects.equals(value, otherBody.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
