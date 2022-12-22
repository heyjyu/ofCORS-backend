package kr.heyjyu.ofcors.dtos;

import java.util.List;

public class QuestionsDto {
    private List<QuestionDto> questions;

    public QuestionsDto(List<QuestionDto> questions) {
        this.questions = questions;
    }

    public List<QuestionDto> getQuestions() {
        return questions;
    }
}
