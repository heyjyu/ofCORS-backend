package kr.heyjyu.ofcors.dtos;

public class QuestionCreationDto {
    private Long id;

    public QuestionCreationDto() {
    }

    public QuestionCreationDto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
