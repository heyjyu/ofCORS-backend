package kr.heyjyu.ofcors.dtos;

public class AuthorDto {
    private Long id;
    private String displayName;

    public AuthorDto() {
    }

    public AuthorDto(Long id, String displayName) {
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
