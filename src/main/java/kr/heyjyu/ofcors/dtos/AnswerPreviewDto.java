package kr.heyjyu.ofcors.dtos;

import java.time.LocalDateTime;
import java.util.Set;

public class AnswerPreviewDto {
    private Long id;
    private QuestionDto question;
    private AuthorDto author;
    private String body;
    private Set<LikeUserIdDto> likeUserIds;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public AnswerPreviewDto() {
    }

    public AnswerPreviewDto(Long id,
                            QuestionDto question,
                            AuthorDto author,
                            String body,
                            Set<LikeUserIdDto> likeUserIds,
                            LocalDateTime createdAt,
                            LocalDateTime updatedAt) {
        this.id = id;
        this.question = question;
        this.author = author;
        this.body = body;
        this.likeUserIds = likeUserIds;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public QuestionDto getQuestion() {
        return question;
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

    public static AnswerPreviewDto fake() {
        return new AnswerPreviewDto(
                1L,
                QuestionDto.fake(),
                AuthorDto.fake(),
                "헤더를 추가해보세요",
                Set.of(new LikeUserIdDto(2L)),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}
