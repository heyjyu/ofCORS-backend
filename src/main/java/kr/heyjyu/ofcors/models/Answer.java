package kr.heyjyu.ofcors.models;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import kr.heyjyu.ofcors.dtos.AnswerCreationDto;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;
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

    @Formula("(SELECT COUNT(*) FROM question_like_user_ids l WHERE l.question_id = id)")
    private int countOfLikes;

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

    public int getCountOfLikes() {
        return countOfLikes;
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

    public void toggleLike(LikeUserId likeUserId) {
        if (likeUserIds.contains(likeUserId)) {
            likeUserIds.remove(likeUserId);

            return;
        }

        likeUserIds.add(likeUserId);
    }

    public boolean isAuthor(Long userId) {
        return authorId.value() == userId;
    }

    public void modify(Body body) {
        this.body = body;
    }
}
