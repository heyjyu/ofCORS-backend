package kr.heyjyu.ofcors.dtos;

import java.util.List;

public class AnswersDto {
    private List<AnswerDto> answers;

    public AnswersDto(List<AnswerDto> answers) {
        this.answers = answers;
    }

    public List<AnswerDto> getAnswers() {
        return answers;
    }
}
