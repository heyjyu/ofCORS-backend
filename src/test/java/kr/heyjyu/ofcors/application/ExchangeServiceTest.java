package kr.heyjyu.ofcors.application;

import kr.heyjyu.ofcors.dtos.AccountVerificationResultDto;
import kr.heyjyu.ofcors.exceptions.InvalidAccount;
import kr.heyjyu.ofcors.exceptions.InvalidExchangeQuantity;
import kr.heyjyu.ofcors.exceptions.NotEnoughPoints;
import kr.heyjyu.ofcors.models.AccountNumber;
import kr.heyjyu.ofcors.models.Bank;
import kr.heyjyu.ofcors.models.Exchange;
import kr.heyjyu.ofcors.models.Points;
import kr.heyjyu.ofcors.models.Quantity;
import kr.heyjyu.ofcors.models.User;
import kr.heyjyu.ofcors.repositories.ExchangeRepository;
import kr.heyjyu.ofcors.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ExchangeServiceTest {
    private ExchangeRepository exchangeRepository;

    private UserRepository userRepository;

    private VerifyAccountService verifyAccountService;

    private ExchangeService exchangeService;

    @BeforeEach
    void setup() {
        exchangeRepository = mock(ExchangeRepository.class);
        userRepository = mock(UserRepository.class);
        verifyAccountService = mock(VerifyAccountService.class);
        exchangeService = new ExchangeService(exchangeRepository, userRepository, verifyAccountService);
    }

    @Test
    void exchange() {
        given(verifyAccountService.verify(any(), any(), any()))
                .willReturn(new AccountVerificationResultDto(true));

        given(userRepository.findById(any()))
                .willReturn(Optional.of(User.fake(new Points(500L))));

        Long userId = 1L;
        Bank bank = new Bank("하나은행");
        AccountNumber accountNumber = new AccountNumber("11111111");
        Quantity quantity = new Quantity(500L);

        Exchange exchange = exchangeService.exchange(userId, bank, accountNumber, quantity);

        assertThat(exchange).isNotNull();

        verify(exchangeRepository).save(any());
    }

    @Test
    void exchangeWithLackingQuantity() {
        given(userRepository.findById(any()))
                .willReturn(Optional.of(User.fake(new Points(500L))));

        Long userId = 1L;
        Bank bank = new Bank("하나은행");
        AccountNumber accountNumber = new AccountNumber("11111111");
        Quantity quantity = new Quantity(1L);

        assertThrows(InvalidExchangeQuantity.class, () -> exchangeService.exchange(userId, bank, accountNumber, quantity));
    }

    @Test
    void exchangeWithNotEnoughPoints() {
        given(userRepository.findById(any()))
                .willReturn(Optional.of(User.fake()));

        given(verifyAccountService.verify(any(), any(), any()))
                .willReturn(new AccountVerificationResultDto(true));

        Long userId = 1L;
        Bank bank = new Bank("하나은행");
        AccountNumber accountNumber = new AccountNumber("11111111");
        Quantity quantity = new Quantity(500L);

        assertThrows(NotEnoughPoints.class, () -> exchangeService.exchange(userId, bank, accountNumber, quantity));
    }

    @Test
    void exchangeWithInvalidAccount() {
        given(userRepository.findById(any()))
                .willReturn(Optional.of(User.fake()));

        given(verifyAccountService.verify(any(), any(), any()))
                .willReturn(new AccountVerificationResultDto(false));

        Long userId = 1L;
        Bank bank = new Bank("하나은행");
        AccountNumber accountNumber = new AccountNumber("11111111");
        Quantity quantity = new Quantity(500L);

        assertThrows(InvalidAccount.class, () -> exchangeService.exchange(userId, bank, accountNumber, quantity));
    }
}
