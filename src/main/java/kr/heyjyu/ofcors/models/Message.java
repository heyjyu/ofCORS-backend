package kr.heyjyu.ofcors.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Message {
    @Column(name = "message")
    private String value;

    public Message() {
    }

    public Message(String value) {
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

        Message otherAbout = (Message) other;

        return Objects.equals(value, otherAbout.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
