package kr.heyjyu.ofcors.dtos;

public class AccountVerificationRequestDto {
    private String bank;
    private String accountNumber;

    public String getBank() {
        return bank;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}
