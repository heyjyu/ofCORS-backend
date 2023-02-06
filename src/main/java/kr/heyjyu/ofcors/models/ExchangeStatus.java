package kr.heyjyu.ofcors.models;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ExchangeStatus {
    PROCESSING("processing"),
    PROCESSED("processed");

    private static final Map<String, String> STATUS_MAP = Collections.unmodifiableMap(
            Stream.of(values()).collect(Collectors.toMap(ExchangeStatus::value, ExchangeStatus::name))
    );

    private String value;

    ExchangeStatus() {
    }

    ExchangeStatus(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static ExchangeStatus of(final String value) {
        return ExchangeStatus.valueOf(STATUS_MAP.get(value));
    }
}
