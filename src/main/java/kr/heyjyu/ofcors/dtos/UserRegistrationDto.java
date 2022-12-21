package kr.heyjyu.ofcors.dtos;

import jakarta.validation.constraints.Pattern;

public class UserRegistrationDto {
    private String displayName;

    private String email;

    private String password;

    public UserRegistrationDto() {
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
