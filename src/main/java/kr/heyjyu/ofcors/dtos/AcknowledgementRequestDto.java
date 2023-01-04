package kr.heyjyu.ofcors.dtos;

public class AcknowledgementRequestDto {
    private Long questionId;
    private Long answerId;
    private String message;
    private Long points;

    public Long getQuestionId() {
        return questionId;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public String getMessage() {
        return message;
    }

    public Long getPoints() {
        return points;
    }
}
