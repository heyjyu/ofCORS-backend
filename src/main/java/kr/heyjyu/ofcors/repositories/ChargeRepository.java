package kr.heyjyu.ofcors.repositories;

import kr.heyjyu.ofcors.models.Charge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChargeRepository extends JpaRepository<Charge, Long> {
}
