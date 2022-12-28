package kr.heyjyu.ofcors.exceptions;

public class UserNotFound extends RuntimeException {
    public UserNotFound(Long id) {
        super("User not found: " + id);
    }
}
