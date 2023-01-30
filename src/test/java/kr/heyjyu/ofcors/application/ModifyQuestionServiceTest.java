package kr.heyjyu.ofcors.application;

import kr.heyjyu.ofcors.dtos.AuthorDto;
import kr.heyjyu.ofcors.dtos.LikeUserIdDto;
import kr.heyjyu.ofcors.dtos.QuestionDto;
import kr.heyjyu.ofcors.dtos.ScrapUserIdDto;
import kr.heyjyu.ofcors.dtos.TagDto;
import kr.heyjyu.ofcors.exceptions.EmptyBody;
import kr.heyjyu.ofcors.exceptions.EmptyTitle;
import kr.heyjyu.ofcors.exceptions.InvalidUser;
import kr.heyjyu.ofcors.exceptions.UnmatchedQuestionId;
import kr.heyjyu.ofcors.exceptions.UnmodifiableQuestion;
import kr.heyjyu.ofcors.models.Answer;
import kr.heyjyu.ofcors.models.Question;
import kr.heyjyu.ofcors.models.QuestionId;
import kr.heyjyu.ofcors.repositories.AnswerRepository;
import kr.heyjyu.ofcors.repositories.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ModifyQuestionServiceTest {
    private QuestionRepository questionRepository;
    private AnswerRepository answerRepository;
    private ModifyQuestionService modifyQuestionService;

    @BeforeEach
    void setup() {
        questionRepository = mock(QuestionRepository.class);
        answerRepository = mock(AnswerRepository.class);
        modifyQuestionService = new ModifyQuestionService(questionRepository, answerRepository);
    }

    @Test
    void modify() {
        given(questionRepository.findById(1L))
                .willReturn(Optional.of(Question.fake()));

        QuestionId questionId = new QuestionId(1L);
        QuestionDto questionDto = QuestionDto.fake();

        Question question = modifyQuestionService.modify(1L, questionId, questionDto);

        assertThat(question).isNotNull();
    }

    @Test
    void modifyQuestionNotMine() {
        given(questionRepository.findById(1L))
                .willReturn(Optional.of(Question.fake()));

        QuestionId questionId = new QuestionId(1L);
        QuestionDto questionDto = QuestionDto.fake();

        assertThrows(InvalidUser.class, () -> modifyQuestionService.modify(2L, questionId, questionDto));
    }

    @Test
    void modifyQuestionWithAnswer() {
        given(questionRepository.findById(1L))
                .willReturn(Optional.of(Question.fake()));

        QuestionDto questionDto = QuestionDto.fake();
        QuestionId questionId = new QuestionId(1L);
        given(answerRepository.findAllByQuestionId(questionId))
                .willReturn(List.of(Answer.fake()));

        assertThrows(UnmodifiableQuestion.class, () -> modifyQuestionService.modify(1L, questionId, questionDto));
    }

    @Test
    void modifyDifferentQuestion() {
        given(questionRepository.findById(2L))
                .willReturn(Optional.of(Question.fake()));

        QuestionDto questionDto = QuestionDto.fake();
        QuestionId questionId = new QuestionId(2L);

        assertThrows(UnmatchedQuestionId.class, () -> modifyQuestionService.modify(1L, questionId, questionDto));
    }

    @Test
    void modifyQuestionWithEmptyTitle() {
        given(questionRepository.findById(1L))
                .willReturn(Optional.of(Question.fake()));

        QuestionDto questionDto = new QuestionDto(
                1L,
                new AuthorDto(1L, "joo", "https://ui-avatars.com/api/?name=Joo&background=0D8ABC&color=fff"),
                "OPEN",
                "",
                "서버 배포 후 CORS에러가 발생합니다",
                Set.of(new TagDto("Web")),
                30L,
                Set.of(new LikeUserIdDto(2L)),
                Set.of(new ScrapUserIdDto(2L)),
                1L,
                2L,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        QuestionId questionId = new QuestionId(1L);

        assertThrows(EmptyTitle.class, () -> modifyQuestionService.modify(1L, questionId, questionDto));
    }

    @Test
    void modifyQuestionWithEmptyBody() {
        given(questionRepository.findById(1L))
                .willReturn(Optional.of(Question.fake()));

        QuestionDto questionDto = new QuestionDto(
                1L,
                new AuthorDto(1L, "joo", "https://ui-avatars.com/api/?name=Joo&background=0D8ABC&color=fff"),
                "OPEN",
                "CORS에러가 발생합니다",
                "",
                Set.of(new TagDto("Web")),
                30L,
                Set.of(new LikeUserIdDto(2L)),
                Set.of(new ScrapUserIdDto(2L)),
                1L,
                2L,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        QuestionId questionId = new QuestionId(1L);

        assertThrows(EmptyBody.class, () -> modifyQuestionService.modify(1L, questionId, questionDto));
    }
}
