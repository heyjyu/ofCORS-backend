package kr.heyjyu.ofcors.specifications;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import kr.heyjyu.ofcors.models.AuthorId;
import kr.heyjyu.ofcors.models.Question;
import kr.heyjyu.ofcors.models.QuestionStatus;
import kr.heyjyu.ofcors.models.ScrapUserId;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

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

    public static Specification<Question> likeTitleOrBody(String keyword) {
        return new Specification<Question>() {
            @Override
            public Predicate toPredicate(Root<Question> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.upper(root.get("body").get("value")), "%" + keyword.toUpperCase() + "%"),
                        criteriaBuilder.like(criteriaBuilder.upper(root.get("title").get("value")), "%" + keyword.toUpperCase() + "%"));
            }
        };
    }

    public static Specification<Question> equalAuthorId(AuthorId authorId) {
        return new Specification<Question>() {
            @Override
            public Predicate toPredicate(Root<Question> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("authorId"), authorId);
            }
        };
    }

    public static Specification<Question> equalScrapUserId(ScrapUserId scrapUserId) {
        return new Specification<Question>() {
            @Override
            public Predicate toPredicate(Root<Question> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.isMember(scrapUserId, root.get("scrapUserIds"));
            }
        };
    }
}
