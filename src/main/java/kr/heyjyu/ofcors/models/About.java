package kr.heyjyu.ofcors.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kr.heyjyu.ofcors.exceptions.InvalidEmail;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Embeddable
public class About {
    @Column(name = "about")
    private String value;

    public About() {
    }

    public About(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        About otherAbout = (About) other;

        return Objects.equals(value, otherAbout.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
