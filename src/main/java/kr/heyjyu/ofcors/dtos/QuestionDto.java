package kr.heyjyu.ofcors.dtos;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class QuestionDto {
    private Long id;
    private AuthorDto author;
    private String status;
    private String title;
    private String body;
    private Set<TagDto> tags;
    private Long points;
    private Set<LikeUserIdDto> likeUserIds;
    private Long hits;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public QuestionDto(Long id,
                       AuthorDto author,
                       String status,
                       String title,
                       String body,
                       Set<TagDto> tags,
                       Long points,
                       Set<LikeUserIdDto> likeUserIds,
                       Long hits,
                       LocalDateTime createdAt,
                       LocalDateTime updatedAt) {
        this.id = id;
        this.author = author;
        this.status = status;
        this.title = title;
        this.body = body;
        this.tags = tags;
        this.points = points;
        this.likeUserIds = likeUserIds;
        this.hits = hits;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public AuthorDto getAuthor() {
        return author;
    }

    public String getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public Set<TagDto> getTags() {
        return tags;
    }

    public Long getPoints() {
        return points;
    }

    public Set<LikeUserIdDto> getLikeUserIds() {
        return likeUserIds;
    }

    public Long getHits() {
        return hits;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public static QuestionDto fake() {
        return new QuestionDto(
                1L,
                new AuthorDto(1L, "joo", "https://ui-avatars.com/api/?name=Joo&background=0D8ABC&color=fff"),
                "OPEN",
                "CORS에러가 발생합니다",
                "서버 배포 후 CORS에러가 발생합니다",
                Set.of(new TagDto("Web")),
                30L,
                Set.of(new LikeUserIdDto(2L)),
                2L,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}
