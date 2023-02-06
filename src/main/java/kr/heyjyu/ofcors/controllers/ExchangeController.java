package kr.heyjyu.ofcors.controllers;

import kr.heyjyu.ofcors.application.ExchangeService;
import kr.heyjyu.ofcors.dtos.ExchangeRequestDto;
import kr.heyjyu.ofcors.dtos.ExchangeResultDto;
import kr.heyjyu.ofcors.models.AccountNumber;
import kr.heyjyu.ofcors.models.Bank;
import kr.heyjyu.ofcors.models.Exchange;
import kr.heyjyu.ofcors.models.Quantity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("exchanges")
public class ExchangeController {
    private ExchangeService exchangeService;

    public ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExchangeResultDto exchange(@RequestAttribute Long userId, @RequestBody ExchangeRequestDto exchangeRequestDto) {
        Bank bank = new Bank(exchangeRequestDto.getBank());
        AccountNumber accountNumber = new AccountNumber(exchangeRequestDto.getAccountNumber());
        Quantity quantity = new Quantity(exchangeRequestDto.getQuantity());

        Exchange exchange = exchangeService.exchange(userId, bank, accountNumber, quantity);

        return exchange.toResultDto();
    }
}
