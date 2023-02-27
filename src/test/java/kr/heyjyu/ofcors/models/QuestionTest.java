package kr.heyjyu.ofcors.models;

import kr.heyjyu.ofcors.dtos.AuthorDto;
import kr.heyjyu.ofcors.dtos.LikeUserIdDto;
import kr.heyjyu.ofcors.dtos.QuestionDto;
import kr.heyjyu.ofcors.dtos.ScrapUserIdDto;
import kr.heyjyu.ofcors.dtos.TagDto;
import kr.heyjyu.ofcors.exceptions.AlreadyAdopted;
import kr.heyjyu.ofcors.exceptions.InvalidUser;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class QuestionTest {
    @Test
    void adopt() {
        Question question = Question.fake();

        Long userId = 1L;
        AnswerId answerId = new AnswerId(1L);

        question.adopt(userId, answerId);

        assertThat(question.getStatus())
                .isEqualTo(QuestionStatus.CLOSED);

        assertThat(question.getSelectedAnswerId())
                .isEqualTo(answerId);

        assertThat(question.isClosed()).isTrue();
    }

    @Test
    void adoptAlreadyAdoptedQuestion() {
        Question question = Question.fake();

        Long userId = 1L;
        AnswerId answerId = new AnswerId(1L);

        question.adopt(userId, answerId);

        assertThrows(AlreadyAdopted.class, () -> question.adopt(userId, answerId));
    }

    @Test
    void adoptQuestionOfOthers() {
        Question question = Question.fake();

        Long userId = 2L;
        AnswerId answerId = new AnswerId(1L);

        assertThrows(InvalidUser.class, () -> question.adopt(userId, answerId));
    }

    @Test
    void toggleLike() {
        Question question = Question.fake();
        LikeUserId likeUserId = new LikeUserId(1L);

        question.toggleLike(likeUserId);

        assertThat(question.getLikeUserIds()).hasSize(1);

        question.toggleLike(likeUserId);

        assertThat(question.getLikeUserIds()).hasSize(0);
    }

    @Test
    void isAuthor() {
        Question question = Question.fake();

        assertThat(question.isAuthor(1L)).isTrue();
        assertThat(question.isAuthor(2L)).isFalse();
    }

    @Test
    void modify() {
        Question question = Question.fake();
        QuestionDto questionDto = new QuestionDto(
                1L,
                new AuthorDto(1L, "joo", "https://ui-avatars.com/api/?name=Joo&background=0D8ABC&color=fff"),
                "OPEN",
                "CORS에러가 발생합니다!",
                "서버 배포 후 CORS에러가 발생합니다!",
                Set.of(new TagDto("Web"), new TagDto("Error")),
                30L,
                Set.of(new LikeUserIdDto(2L)),
                Set.of(new ScrapUserIdDto(2L)),
                1L,
                2L,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        question.modify(questionDto);

        assertThat(question.getTitle().value()).isEqualTo(questionDto.getTitle());
        assertThat(question.getBody().value()).isEqualTo(questionDto.getBody());
        assertThat(question.getTags()).hasSize(2);
    }

    @Test
    void addScrappedUser() {
        Question question = Question.fake();

        question.addScrappedUser(100L);

        assertThat(question.getScrapUserIds()).contains(new ScrapUserId(100L));
    }

    @Test
    void removeScrappedUser() {
        Question question = Question.fake();

        question.removeScrappedUser(100L);

        assertThat(question.getScrapUserIds()).doesNotContain(new ScrapUserId(100L));
    }

    @Test
    void visit() {
        Question question = Question.fake();

        String ip = "0:0:0:0:0:0:0:1";

        question.visit(ip);

        assertThat(question.getHits()).isEqualTo(new Hits(1L));

        question.visit(ip);

        assertThat(question.getHits()).isEqualTo(new Hits(1L));

        question.visit("0:0:0:0:0:0:0:2");

        assertThat(question.getHits()).isEqualTo(new Hits(2L));
    }
}
