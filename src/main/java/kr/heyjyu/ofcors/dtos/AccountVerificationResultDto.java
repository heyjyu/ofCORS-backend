package kr.heyjyu.ofcors.dtos;

public class AccountVerificationResultDto {
    private boolean validated;

    public AccountVerificationResultDto() {
    }

    public AccountVerificationResultDto(boolean validated) {
        this.validated = validated;
    }

    public boolean getValidated() {
        return validated;
    }
}
