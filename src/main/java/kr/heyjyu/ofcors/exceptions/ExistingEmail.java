package kr.heyjyu.ofcors.exceptions;

import kr.heyjyu.ofcors.models.Email;

public class ExistingEmail extends SignUpFailed {
    public ExistingEmail(Email email) {
        super(email.value());
    }
}
