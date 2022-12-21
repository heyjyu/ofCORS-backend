package kr.heyjyu.ofcors.repositories;

import kr.heyjyu.ofcors.models.Email;
import kr.heyjyu.ofcors.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByEmail(Email email);

    Boolean existsByEmail(Email email);
}
