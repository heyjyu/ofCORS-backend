package kr.heyjyu.ofcors.dtos;

public class AcknowledgementCreationDto {
    private Long id;

    public AcknowledgementCreationDto() {
    }

    public AcknowledgementCreationDto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
