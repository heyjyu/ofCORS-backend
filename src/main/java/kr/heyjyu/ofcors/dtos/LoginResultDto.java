package kr.heyjyu.ofcors.dtos;

public class LoginResultDto {
    String accessToken;

    public LoginResultDto() {
    }

    public LoginResultDto(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
