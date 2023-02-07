package kr.heyjyu.ofcors.application;

import jakarta.transaction.Transactional;
import kr.heyjyu.ofcors.dtos.ExchangeDto;
import kr.heyjyu.ofcors.models.Exchange;
import kr.heyjyu.ofcors.models.UserId;
import kr.heyjyu.ofcors.repositories.ExchangeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class GetExchangesService {
    private ExchangeRepository exchangeRepository;

    public GetExchangesService(ExchangeRepository exchangeRepository) {
        this.exchangeRepository = exchangeRepository;
    }

    public List<ExchangeDto> getExchanges(Long userId) {
        List<Exchange> exchanges = exchangeRepository.findAllByUserId(new UserId(userId));

        return exchanges.stream()
                .map(exchange -> new ExchangeDto(
                        exchange.getId(),
                        exchange.getQuantity().value(),
                        exchange.totalAmount(),
                        exchange.getStatus().value(),
                        exchange.getCreatedAt()
                        ))
                .collect(Collectors.toList());
    }
}
