package kr.heyjyu.ofcors.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class ScrapId {
    @Column(name = "scrapId")
    private String value;

    public ScrapId() {
    }

    public ScrapId(String value) {
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

        ScrapId otherScrapId = (ScrapId) other;

        return Objects.equals(value, otherScrapId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
