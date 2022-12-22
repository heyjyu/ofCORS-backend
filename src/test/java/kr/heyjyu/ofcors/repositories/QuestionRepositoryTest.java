package kr.heyjyu.ofcors.repositories;

import jakarta.transaction.Transactional;
import kr.heyjyu.ofcors.models.LikeUserId;
import kr.heyjyu.ofcors.models.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class QuestionRepositoryTest {
    @Autowired
    QuestionRepository questionRepository;

    @Test
    void findAllByLikesCount() {
        Set<LikeUserId> likeUserIds1 = Set.of(new LikeUserId(1L), new LikeUserId(2L), new LikeUserId(3L));
        Set<LikeUserId> likeUserIds2 = Set.of(new LikeUserId(1L), new LikeUserId(2L), new LikeUserId(3L),  new LikeUserId(4L));
        Set<LikeUserId> likeUserIds3 = Set.of(new LikeUserId(1L));

        Question question1 = new Question(likeUserIds1);
        Question question2 = new Question(likeUserIds2);
        Question question3 = new Question(likeUserIds3);

        questionRepository.save(question1);
        questionRepository.save(question2);
        questionRepository.save(question3);

        List<Question> questions = questionRepository.findAllOrderByLikesCount();

        assertThat(questions.get(0).getLikeUserIds()).hasSize(4);
        assertThat(questions.get(1).getLikeUserIds()).hasSize(3);
        assertThat(questions.get(2).getLikeUserIds()).hasSize(1);
    }
}
