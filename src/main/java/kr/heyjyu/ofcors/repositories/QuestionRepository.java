package kr.heyjyu.ofcors.repositories;

import kr.heyjyu.ofcors.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("SELECT q FROM Question q ORDER BY size(q.likeUserIds) DESC")
    List<Question> findAllOrderByLikesCount();
}
