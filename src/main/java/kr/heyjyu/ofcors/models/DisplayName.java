package kr.heyjyu.ofcors.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kr.heyjyu.ofcors.exceptions.InvalidDisplayName;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Embeddable
public class DisplayName {
    @Column(name = "displayName")
    private String value;

    public DisplayName() {
    }

    public DisplayName(String value) {
        Pattern pattern = Pattern.compile("^.{3,}$");
        Matcher matcher = pattern.matcher(value);

        if (!matcher.find()) {
            throw new InvalidDisplayName();
        }

        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        DisplayName otherDisplayName = (DisplayName) other;

        return Objects.equals(value, otherDisplayName.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
