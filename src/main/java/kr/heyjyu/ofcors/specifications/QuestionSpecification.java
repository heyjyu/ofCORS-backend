package kr.heyjyu.ofcors.specifications;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import kr.heyjyu.ofcors.models.Question;
import kr.heyjyu.ofcors.models.QuestionStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.Collection;

public class QuestionSpecification {
    public static Specification<Question> inPeriod(String period) {
        if (period.equals("week")) {
            return new Specification<Question>() {
                @Override
                public Predicate toPredicate(Root<Question> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    return criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), LocalDateTime.now().minusWeeks(1));
                }
            };
        }

        if (period.equals("month")) {
            return new Specification<Question>() {
                @Override
                public Predicate toPredicate(Root<Question> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    return criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), LocalDateTime.now().minusMonths(1));
                }
            };
        }

        return (root, query, criteriaBuilder) -> null;
    }

    public static Specification<Question> equalStatus(QuestionStatus status) {
        return new Specification<Question>() {
            @Override
            public Predicate toPredicate(Root<Question> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("status"), status);
            }
        };
    }
}