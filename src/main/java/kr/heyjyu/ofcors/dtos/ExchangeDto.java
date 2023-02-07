package kr.heyjyu.ofcors.dtos;

import java.time.LocalDateTime;
import java.util.Set;

public class ExchangeDto {
    private Long id;
    private Long quantity;
    private Long totalAmount;
    private String status;
    private LocalDateTime createdAt;

    public ExchangeDto(Long id, Long quantity, Long totalAmount, String status, LocalDateTime createdAt) {
        this.id = id;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getQuantity() {
        return quantity;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public static ExchangeDto fake() {
        return new ExchangeDto(
                1L,
                500L,
                29010L,
                "inProgress",
                LocalDateTime.now()
        );
    }
}
