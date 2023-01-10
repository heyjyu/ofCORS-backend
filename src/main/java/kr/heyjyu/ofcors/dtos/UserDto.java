package kr.heyjyu.ofcors.dtos;

import java.util.Set;

public class UserDto {
    private Long id;
    private String displayName;
    private String about;
    private Long points;
    private String realName;
    private String imageUrl;
    private Long countOfLikes;
    private Set<TagDto> tags;

    public UserDto() {
    }

    public UserDto(Long id, String displayName, String about, Long points, String realName, String imageUrl, Long countOfLikes, Set<TagDto> tags) {
        this.id = id;
        this.displayName = displayName;
        this.about = about;
        this.points = points;
        this.realName = realName;
        this.imageUrl = imageUrl;
        this.countOfLikes = countOfLikes;
        this.tags = tags;
    }

    public static UserDto fake() {
        return new UserDto(
                1L,
                "hong",
                "이런 사람 입니다.",
                100L,
                "홍길동",
                "http://image.url",
                1L,
                Set.of(new TagDto("Web"))
        );
    }

    public Long getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getAbout() {
        return about;
    }

    public Long getPoints() {
        return points;
    }

    public String getRealName() {
        return realName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Long getCountOfLikes() {
        return countOfLikes;
    }

    public Set<TagDto> getTags() {
        return tags;
    }
}
