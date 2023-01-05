package kr.heyjyu.ofcors.exceptions;

import kr.heyjyu.ofcors.models.AnswerId;

public class AnswerNotFound extends RuntimeException {
    public AnswerNotFound(Long id) {
        super("Answer not found: " + id);
    }

    public AnswerNotFound(AnswerId answerId) {
        super("Answer not found: " + answerId.value());
    }
}
