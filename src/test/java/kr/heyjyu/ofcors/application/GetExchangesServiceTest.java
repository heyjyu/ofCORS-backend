package kr.heyjyu.ofcors.application;

import kr.heyjyu.ofcors.models.Exchange;
import kr.heyjyu.ofcors.repositories.ExchangeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class GetExchangesServiceTest {
    private ExchangeRepository exchangeRepository;
    private GetExchangesService getExchangesService;

    @BeforeEach
    void setup() {
        exchangeRepository = mock(ExchangeRepository.class);
        getExchangesService = new GetExchangesService(exchangeRepository);
    }

    @Test
    void getExchanges() {
        given(exchangeRepository.findAllByUserId(any()))
                .willReturn(List.of(Exchange.fake()));

        assertThat(getExchangesService.getExchanges(1L)).hasSize(1);
    }
}
