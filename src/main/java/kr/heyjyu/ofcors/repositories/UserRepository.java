package kr.heyjyu.ofcors.repositories;

import kr.heyjyu.ofcors.models.Email;
import kr.heyjyu.ofcors.models.Question;
import kr.heyjyu.ofcors.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor {
    Page<User> findAll(Specification specification, Pageable pageable);

    Page<User> findAll(Pageable pageable);

    List<User> findAllByEmail(Email email);

    Boolean existsByEmail(Email email);

    Optional<User> findByEmail(Email email);
}
