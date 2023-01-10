package kr.heyjyu.ofcors.specifications;

import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import kr.heyjyu.ofcors.models.Tag;
import kr.heyjyu.ofcors.models.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Set;

public class UserSpecification {
    public static Specification<User> likeDisplayNameOrTag(String keyword) {
        return new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.upper(root.join("tags", JoinType.LEFT).get("value")), "%" + keyword.toUpperCase() + "%"),
                        criteriaBuilder.like(criteriaBuilder.upper(root.get("displayName").get("value")), "%" + keyword.toUpperCase() + "%")
                );
            }
        };
    }
}
