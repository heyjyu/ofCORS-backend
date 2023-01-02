package kr.heyjyu.ofcors.application;

import kr.heyjyu.ofcors.models.Body;
import kr.heyjyu.ofcors.models.Points;
import kr.heyjyu.ofcors.models.Question;
import kr.heyjyu.ofcors.models.Tag;
import kr.heyjyu.ofcors.models.Title;
import kr.heyjyu.ofcors.models.User;
import kr.heyjyu.ofcors.repositories.QuestionRepository;
import kr.heyjyu.ofcors.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CreateQuestionServiceTest {
    private CreateQuestionService createQuestionService;
    private QuestionRepository questionRepository;
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        questionRepository = mock(QuestionRepository.class);
        createQuestionService = new CreateQuestionService(questionRepository, userRepository);
    }

    @Test
    void create() {
        User user = User.fake();
        user.setInitialPoint();
        Points initialPoints = user.getPoints();

        given(userRepository.findById(any()))
                .willReturn(Optional.of(user));

        Long userId = 1L;
        Title title = new Title("CORS 에러가 발생합니다.");
        Body body = new Body("서버 배포 후 CORS에러가 발생합니다");
        Set<Tag> tags = Set.of(new Tag("Web"));
        Points points = new Points(30L);

        Question question = createQuestionService.create(userId, title, body, tags, points);

        assertThat(question).isNotNull();
        assertThat(user.getPoints()).isEqualTo(new Points(initialPoints.value() - points.value()));
        verify(questionRepository).save(any(Question.class));
    }
}
