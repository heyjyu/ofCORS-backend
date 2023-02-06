package kr.heyjyu.ofcors.dtos;

public class ExchangeRequestDto {
    private Long quantity;

    private String bank;

    private String accountNumber;

    public Long getQuantity() {
        return quantity;
    }

    public String getBank() {
        return bank;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}
