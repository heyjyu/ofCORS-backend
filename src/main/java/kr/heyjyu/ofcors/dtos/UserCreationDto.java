package kr.heyjyu.ofcors.dtos;

import kr.heyjyu.ofcors.models.DisplayName;

public class UserCreationDto {
    private Long id;
    private String displayName;

    public UserCreationDto() {
    }

    public UserCreationDto(Long id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    public Long getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }
}
