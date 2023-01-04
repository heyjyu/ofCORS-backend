package kr.heyjyu.ofcors.models;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import kr.heyjyu.ofcors.dtos.AnswerCreationDto;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Answer {
    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private QuestionId questionId;

    @ElementCollection
    private Set<LikeUserId> likeUserIds;

    @Embedded
    private Body body;

    @Embedded
    private AuthorId authorId;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Answer() {
    }

    public Answer(QuestionId questionId, Body body, AuthorId authorId) {
        this.questionId = questionId;
        this.body = body;
        this.authorId = authorId;
        this.likeUserIds = new HashSet<>();
    }

    public static Answer fake() {
        return new Answer(
                new QuestionId(1L),
                new Body("헤더를 붙여보세요"),
                new AuthorId(3L)
        );
    }

    public Long getId() {
        return id;
    }

    public QuestionId getQuestionId() {
        return questionId;
    }

    public Set<LikeUserId> getLikeUserIds() {
        return likeUserIds;
    }

    public Body getBody() {
        return body;
    }

    public AuthorId getAuthorId() {
        return authorId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public AnswerCreationDto toCreationDto() {
        return new AnswerCreationDto(id);
    }
}
