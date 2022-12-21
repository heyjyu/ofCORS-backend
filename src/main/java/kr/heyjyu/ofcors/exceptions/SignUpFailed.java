package kr.heyjyu.ofcors.exceptions;

public class SignUpFailed extends RuntimeException {
    public SignUpFailed() {
    }

    public SignUpFailed(String value) {
        super(value);
    }
}
