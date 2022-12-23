package kr.heyjyu.ofcors.models;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import kr.heyjyu.ofcors.dtos.QuestionDto;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Question {
    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private AuthorId authorId;

    @Embedded
    private QuestionStatus status;

    @Embedded
    private Title title;

    @Embedded
    private Body body;

    @ElementCollection
    private Set<Tag> tags = new HashSet<>();

    @Embedded
    private Points points;

    @ElementCollection
    private Set<LikeUserId> likeUserIds = new HashSet<>();

    @Formula("(SELECT COUNT(*) FROM question_like_user_ids l WHERE l.question_id = id)")
    private int countOfLikes;

    @Embedded
    private Hits hits;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Question() {
    }

    public Question(Set<LikeUserId> likeUserIds) {
        this.likeUserIds = likeUserIds;
    }

    public Long getId() {
        return id;
    }

    public AuthorId getAuthorId() {
        return authorId;
    }

    public QuestionStatus getStatus() {
        return status;
    }

    public Title getTitle() {
        return title;
    }

    public Body getBody() {
        return body;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public Points getPoints() {
        return points;
    }

    public Set<LikeUserId> getLikeUserIds() {
        return likeUserIds;
    }

    public int getCountOfLikes() {
        return countOfLikes;
    }

    public Hits getHits() {
        return hits;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public QuestionDto toDto() {
        return new QuestionDto(id,
                status.value(),
                title.value(),
                body.value(),
                tags.stream().map(Tag::toDto).collect(Collectors.toSet()),
                points.value(),
                likeUserIds.stream().map(LikeUserId::toDto).collect(Collectors.toSet()),
                hits.value(),
                createdAt,
                updatedAt);
    }
}
