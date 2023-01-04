package kr.heyjyu.ofcors.repositories;

import kr.heyjyu.ofcors.models.Acknowledgement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcknowledgementRepository extends JpaRepository<Acknowledgement, Long> {
}
