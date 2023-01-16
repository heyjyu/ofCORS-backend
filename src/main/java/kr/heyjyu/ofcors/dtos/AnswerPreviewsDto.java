package kr.heyjyu.ofcors.dtos;

import java.util.List;

public class AnswerPreviewsDto {
    private List<AnswerPreviewDto> answerPreviews;

    public AnswerPreviewsDto(List<AnswerPreviewDto> answerPreviews) {
        this.answerPreviews = answerPreviews;
    }

    public List<AnswerPreviewDto> getAnswerPreviews() {
        return answerPreviews;
    }
}
