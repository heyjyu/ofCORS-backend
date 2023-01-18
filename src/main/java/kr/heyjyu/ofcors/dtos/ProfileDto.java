package kr.heyjyu.ofcors.dtos;

import java.util.Set;

public class ProfileDto {
    private String displayName;
    private String about;
    private String imageUrl;
    private Set<String> tags;

    public ProfileDto() {
    }

    public ProfileDto(String displayName, String about, String imageUrl, Set<String> tags) {
        this.displayName = displayName;
        this.about = about;
        this.imageUrl = imageUrl;
        this.tags = tags;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getAbout() {
        return about;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Set<String> getTags() {
        return tags;
    }

    public static ProfileDto fake() {
        return new ProfileDto(
                "hong",
                "저는 함수형 프로그래밍을 좋아합니다",
                "https://image.com",
                Set.of("Web")
        );
    }
}
