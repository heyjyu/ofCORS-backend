package kr.heyjyu.ofcors.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum QuestionStatus {
    OPEN("open"),
    CLOSED("closed");

    private static final Map<String, String> STATUS_MAP = Collections.unmodifiableMap(
            Stream.of(values()).collect(Collectors.toMap(QuestionStatus::value, QuestionStatus::name))
    );

    private String value;

    QuestionStatus() {
    }

    QuestionStatus(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static QuestionStatus of(final String value) {
        return QuestionStatus.valueOf(STATUS_MAP.get(value));
    }
}
