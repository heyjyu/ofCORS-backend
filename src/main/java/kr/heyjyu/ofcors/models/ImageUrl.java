package kr.heyjyu.ofcors.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class ImageUrl {
    @Column(name = "imageUrl")
    private String value;

    public ImageUrl() {
    }

    public ImageUrl(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        ImageUrl otherAbout = (ImageUrl) other;

        return Objects.equals(value, otherAbout.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
