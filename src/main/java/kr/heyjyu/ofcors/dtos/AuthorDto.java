package kr.heyjyu.ofcors.dtos;

public class AuthorDto {
    private Long id;
    private String displayName;
    private String imageUrl;

    public AuthorDto() {
    }

    public AuthorDto(Long id, String displayName, String imageUrl) {
        this.id = id;
        this.displayName = displayName;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public static AuthorDto fake() {
        return new AuthorDto(1L, "joo", "https://ui-avatars.com/api/?name=Joo&background=0D8ABC&color=fff");
    }
}
