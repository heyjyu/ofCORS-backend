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
        ScrapId otherScrapId = (ScrapId) other;

        return Objects.equals(value, otherScrapId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
