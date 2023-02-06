package kr.heyjyu.ofcors.models;

public class IamPortAccessToken {
    private Integer code;

    private String message;

    private IamPortAccessTokenResponse response;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public IamPortAccessTokenResponse getResponse() {
        return response;
    }
}
