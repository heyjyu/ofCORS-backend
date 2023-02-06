package kr.heyjyu.ofcors.models;

public class IamPortAccessTokenResponse {
    private String access_token;
    private Integer now;
    private Integer expired_at;

    public String getAccess_token() {
        return access_token;
    }

    public Integer getNow() {
        return now;
    }

    public Integer getExpired_at() {
        return expired_at;
    }
}
