package kr.heyjyu.ofcors.dtos;

import java.time.LocalDateTime;
import java.util.Set;

public class AnswerDto {
    private Long id;
    private Long questionId;
    private AuthorDto author;
    private String body;
    private Set<LikeUserIdDto> likeUserIds;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public AnswerDto() {
    }

    public AnswerDto(Long id
            , Long questionId
            , AuthorDto author
            , String body
            , Set<LikeUserIdDto> likeUserIds
            , LocalDateTime createdAt
            , LocalDateTime updatedAt) {
        this.id = id;
        this.questionId = questionId;
        this.author = author;
        this.body = body;
        this.likeUserIds = likeUserIds;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public AuthorDto getAuthor() {
        return author;
    }

    public String getBody() {
        return body;
    }

    public Set<LikeUserIdDto> getLikeUserIds() {
        return likeUserIds;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public static AnswerDto fake() {
        return new AnswerDto(
                1L,
                1L,
                new AuthorDto(1L, "joo", "https://ui-avatars.com/api/?name=Joo&background=0D8ABC&color=fff"),
                "헤더를 추가해보세요",
                Set.of(new LikeUserIdDto(2L)),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}
