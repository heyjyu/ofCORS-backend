package kr.heyjyu.ofcors.repositories;

import kr.heyjyu.ofcors.models.Answer;
import kr.heyjyu.ofcors.models.QuestionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findAllByQuestionId(QuestionId questionId);
}
