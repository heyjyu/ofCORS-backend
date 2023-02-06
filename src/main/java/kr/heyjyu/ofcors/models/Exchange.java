package kr.heyjyu.ofcors.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import kr.heyjyu.ofcors.dtos.ExchangeRequestDto;
import kr.heyjyu.ofcors.dtos.ExchangeResultDto;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
public class Exchange {
    @Id
    @GeneratedValue
    private Long id;

    private Quantity quantity;

    private UserId userId;

    private Bank bank;

    private AccountNumber accountNumber;

    private ExchangeStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Exchange(Quantity quantity, UserId userId, Bank bank, AccountNumber accountNumber) {
        this.quantity = quantity;
        this.userId = userId;
        this.bank = bank;
        this.accountNumber = accountNumber;
    }

    public static Exchange fake() {
        return new Exchange(new Quantity(500L),
                new UserId(1L),
                new Bank("하나은행"),
                new AccountNumber("11111111"));
    }

    public ExchangeResultDto toResultDto() {
        return new ExchangeResultDto(id);
    }
}
