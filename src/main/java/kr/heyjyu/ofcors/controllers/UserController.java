package kr.heyjyu.ofcors.controllers;

import kr.heyjyu.ofcors.application.CountUserService;
import kr.heyjyu.ofcors.application.CreateUserService;
import kr.heyjyu.ofcors.application.GetUserService;
import kr.heyjyu.ofcors.dtos.UserCountDto;
import kr.heyjyu.ofcors.dtos.UserCreationDto;
import kr.heyjyu.ofcors.dtos.UserDto;
import kr.heyjyu.ofcors.dtos.UserRegistrationDto;
import kr.heyjyu.ofcors.exceptions.AuthenticationError;
import kr.heyjyu.ofcors.exceptions.SignUpFailed;
import kr.heyjyu.ofcors.models.DisplayName;
import kr.heyjyu.ofcors.models.Email;
import kr.heyjyu.ofcors.models.Password;
import kr.heyjyu.ofcors.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {
    private GetUserService getUserService;
    private CountUserService countUserService;
    private CreateUserService createUserService;

    public UserController(GetUserService getUserService,
                          CountUserService countUserService,
                          CreateUserService createUserService) {
        this.getUserService = getUserService;
        this.countUserService = countUserService;
        this.createUserService = createUserService;
    }

    @GetMapping("me")
    public UserDto user(@RequestAttribute Long userId) {
        User user = getUserService.getUser(userId);

        return user.toDto();
    }

    @GetMapping
    public UserCountDto userCount(@RequestParam boolean countOnly, String email) {
        if (countOnly) {
            return new UserCountDto(countUserService.count(new Email(email)));
        }

        return null;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserCreationDto signUp(
            @RequestBody UserRegistrationDto userRegistrationDto) {
        DisplayName displayName = new DisplayName(userRegistrationDto.getDisplayName());
        Email email = new Email(userRegistrationDto.getEmail());
        Password password = new Password(userRegistrationDto.getPassword());

        User user = createUserService.create(displayName, email, password);

        return user.toCreationDto();
    }

    @ExceptionHandler(SignUpFailed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String signUpFailed() {
        return "Sign up failed!";
    }
}
