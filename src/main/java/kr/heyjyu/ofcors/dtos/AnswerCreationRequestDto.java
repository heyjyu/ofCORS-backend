package kr.heyjyu.ofcors.dtos;

public class AnswerCreationRequestDto {
    private Long questionId;
    private String body;

    public AnswerCreationRequestDto() {
    }

    public Long getQuestionId() {
        return questionId;
    }

    public String getBody() {
        return body;
    }
}
