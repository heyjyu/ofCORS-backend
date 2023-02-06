package kr.heyjyu.ofcors.exceptions;

import org.springframework.web.client.RestClientException;

public class IamPortAccessTokenException extends RuntimeException {
    public IamPortAccessTokenException(RestClientException e) {
        super("IamPortError " + e);
    }
}
