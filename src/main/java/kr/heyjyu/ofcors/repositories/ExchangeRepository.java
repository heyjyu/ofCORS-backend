package kr.heyjyu.ofcors.repositories;

import kr.heyjyu.ofcors.models.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeRepository extends JpaRepository<Exchange, Long> {
}
