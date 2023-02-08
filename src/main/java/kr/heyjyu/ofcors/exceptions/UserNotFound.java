package kr.heyjyu.ofcors.exceptions;

import kr.heyjyu.ofcors.models.UserId;

public class UserNotFound extends RuntimeException {
    public UserNotFound(Long id) {
        super("User not found: " + id);
    }

    public UserNotFound(UserId userId) {
        super("User not found: " + userId.value());
    }
}
