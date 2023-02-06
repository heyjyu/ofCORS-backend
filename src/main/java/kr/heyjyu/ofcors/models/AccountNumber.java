package kr.heyjyu.ofcors.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class AccountNumber {
    @Column(name = "accountNumber")
    private String value;

    public AccountNumber() {
    }

    public AccountNumber(String value) {
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

        AccountNumber otherAccountNumber = (AccountNumber) other;

        return Objects.equals(value, otherAccountNumber.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
