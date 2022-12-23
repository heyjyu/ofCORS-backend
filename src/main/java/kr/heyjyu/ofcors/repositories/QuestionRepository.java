package kr.heyjyu.ofcors.repositories;

import kr.heyjyu.ofcors.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface QuestionRepository extends JpaRepository<Question, Long>, JpaSpecificationExecutor {
}
