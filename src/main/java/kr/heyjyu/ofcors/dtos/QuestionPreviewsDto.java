package kr.heyjyu.ofcors.dtos;

import java.util.List;

public class QuestionPreviewsDto {
    private List<QuestionPreviewDto> questionPreviews;

    public QuestionPreviewsDto(List<QuestionPreviewDto> questionPreviews) {
        this.questionPreviews = questionPreviews;
    }

    public List<QuestionPreviewDto> getQuestionPreviews() {
        return questionPreviews;
    }
}
