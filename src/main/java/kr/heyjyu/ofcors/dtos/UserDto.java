package kr.heyjyu.ofcors.dtos;

public class UserDto {
    private String displayName;
    private String about;
    private Long points;
    private String realName;
    private String imageUrl;

    public UserDto() {
    }

    public UserDto(String displayName, String about, Long points, String realName, String imageUrl) {
        this.displayName = displayName;
        this.about = about;
        this.points = points;
        this.realName = realName;
        this.imageUrl = imageUrl;
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
}