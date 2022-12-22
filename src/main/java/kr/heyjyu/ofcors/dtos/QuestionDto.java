package kr.heyjyu.ofcors.dtos;

import java.time.LocalDateTime;
import java.util.Set;

public class QuestionDto {
    private Long id;
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
}
