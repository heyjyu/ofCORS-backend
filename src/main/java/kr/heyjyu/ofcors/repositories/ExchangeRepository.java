package kr.heyjyu.ofcors.repositories;

import kr.heyjyu.ofcors.models.Exchange;
import kr.heyjyu.ofcors.models.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExchangeRepository extends JpaRepository<Exchange, Long> {
    List<Exchange> findAllByUserId(UserId userId);
}
