package kr.heyjyu.ofcors.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class QuestionStatus {
    private static final QuestionStatus OPEN =
            new QuestionStatus("open");
    private static final QuestionStatus CLOSED =
            new QuestionStatus("closed");

    @Column(name = "questionStatus")
    private String value;

    public QuestionStatus() {
    }

    public QuestionStatus(String value) {
        this.value = value;
    }

    public static QuestionStatus open() {
        return OPEN;
    }

    public static QuestionStatus closed() {
        return CLOSED;
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

        QuestionStatus otherQuestionStatus = (QuestionStatus) other;

        return Objects.equals(value, otherQuestionStatus.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
