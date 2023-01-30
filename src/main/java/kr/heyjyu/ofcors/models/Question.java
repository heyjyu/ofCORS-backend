package kr.heyjyu.ofcors.models;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import kr.heyjyu.ofcors.dtos.QuestionCreationDto;
import kr.heyjyu.ofcors.dtos.QuestionDto;
import kr.heyjyu.ofcors.dtos.QuestionModificationDto;
import kr.heyjyu.ofcors.dtos.QuestionScrapResultDto;
import kr.heyjyu.ofcors.exceptions.AlreadyAdopted;
import kr.heyjyu.ofcors.exceptions.InvalidUser;
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

    @Enumerated(EnumType.STRING)
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

    @ElementCollection
    private Set<ScrapUserId> scrapUserIds = new HashSet<>();

    @Embedded
    @AttributeOverride(name="value", column = @Column(name = "SELECTED_ANSWER_ID"))
    private AnswerId selectedAnswerId;

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

    public Question(AuthorId authorId, Title title, Body body, Set<Tag> tags, Points points) {
        this.authorId = authorId;
        this.title = title;
        this.body = body;
        this.tags = tags;
        this.points = points;
        this.status = QuestionStatus.OPEN;
        this.selectedAnswerId = new AnswerId(-1L);
        this.hits = new Hits(0L);
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

    public Set<ScrapUserId> getScrapUserIds() {
        return scrapUserIds;
    }

    public AnswerId getSelectedAnswerId() {
        return selectedAnswerId;
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

    public void adopt(Long userId, AnswerId answerId) {
        if (this.authorId.value() != userId) {
            throw new InvalidUser();
        }

        if (this.status == QuestionStatus.CLOSED || this.selectedAnswerId.value() > 0){
            throw new AlreadyAdopted();
        }

        this.status = QuestionStatus.CLOSED;
        this.selectedAnswerId = answerId;
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

    public void modify(QuestionDto questionDto) {
        title = new Title(questionDto.getTitle());
        body = new Body(questionDto.getBody());
        tags = questionDto.getTags().stream().map(tag -> new Tag(tag.getName()))
                .collect(Collectors.toSet());
    }

    public boolean isClosed() {
        return status == QuestionStatus.CLOSED;
    }

    public void addScrappedUser(Long userId) {
        this.scrapUserIds.add(new ScrapUserId(userId));
    }

    public void removeScrappedUser(Long userId) {
        this.scrapUserIds.remove(new ScrapUserId(userId));
    }

    public QuestionCreationDto toCreationDto() {
        return new QuestionCreationDto(id);
    }

    public QuestionModificationDto toModificationDto() {
        return new QuestionModificationDto(id);
    }

    public QuestionScrapResultDto toScrapResultDto() {
        return new QuestionScrapResultDto(scrapUserIds.stream().map(ScrapUserId::toDto).collect(Collectors.toSet()));
    }

    public static Question fake() {
        AuthorId authorId = new AuthorId(1L);
        Title title = new Title("CORS 에러가 발생합니다");
        Body body = new Body("서버 배포 후 CORS에러가 발생합니다.");
        Set<Tag> tags = Set.of(new Tag("Web"));
        Points points = new Points(30L);

        return new Question(authorId, title, body, tags, points);
    }
}
