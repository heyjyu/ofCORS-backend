package kr.heyjyu.ofcors.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kr.heyjyu.ofcors.exceptions.InvalidEmail;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Embeddable
public class Email {
    @Column(name = "email")
    private String value;

    public Email() {
    }

    public Email(String value) {
        Pattern pattern = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");
        Matcher matcher = pattern.matcher(value);

        if (!matcher.find()) {
            throw new InvalidEmail();
        }

        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        Email otherEmail = (Email) other;

        return Objects.equals(value, otherEmail.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

