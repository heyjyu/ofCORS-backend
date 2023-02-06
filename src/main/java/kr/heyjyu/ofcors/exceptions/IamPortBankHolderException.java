package kr.heyjyu.ofcors.exceptions;

import org.springframework.web.client.RestClientException;

public class IamPortBankHolderException extends RuntimeException {
    public IamPortBankHolderException(RestClientException e) {
        super("IamPortError " + e);
    }
}
