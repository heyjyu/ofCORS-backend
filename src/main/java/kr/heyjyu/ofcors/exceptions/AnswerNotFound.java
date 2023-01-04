package kr.heyjyu.ofcors.exceptions;

public class AnswerNotFound extends RuntimeException {
    public AnswerNotFound(Long id) {
        super("Answer not found: " + id);
    }
}
