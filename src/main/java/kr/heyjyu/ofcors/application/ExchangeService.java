package kr.heyjyu.ofcors.application;

import jakarta.transaction.Transactional;
import kr.heyjyu.ofcors.exceptions.InvalidAccount;
import kr.heyjyu.ofcors.exceptions.InvalidExchangeQuantity;
import kr.heyjyu.ofcors.exceptions.UserNotFound;
import kr.heyjyu.ofcors.models.AccountNumber;
import kr.heyjyu.ofcors.models.Bank;
import kr.heyjyu.ofcors.models.Exchange;
import kr.heyjyu.ofcors.models.Quantity;
import kr.heyjyu.ofcors.models.User;
import kr.heyjyu.ofcors.models.UserId;
import kr.heyjyu.ofcors.repositories.ExchangeRepository;
import kr.heyjyu.ofcors.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ExchangeService {
    private ExchangeRepository exchangeRepository;
    private UserRepository userRepository;
    private VerifyAccountService verifyAccountService;

    public ExchangeService(ExchangeRepository exchangeRepository, UserRepository userRepository, VerifyAccountService verifyAccountService) {
        this.exchangeRepository = exchangeRepository;
        this.userRepository = userRepository;
        this.verifyAccountService = verifyAccountService;
    }

    public Exchange exchange(Long userId, Bank bank, AccountNumber accountNumber, Quantity quantity) {
        Long minimumExchangeQuantity = 500L;

        if (quantity.lessThan(new Quantity(minimumExchangeQuantity))) {
            throw new InvalidExchangeQuantity();
        }

        if (!verifyAccountService.verify(userId, bank, accountNumber).getValidated()) {
            throw new InvalidAccount();
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFound(userId));

        user.exchangePoints(quantity);

        Exchange exchange = new Exchange(quantity, new UserId(userId), bank, accountNumber);

        exchangeRepository.save(exchange);

        return exchange;
    }
}
