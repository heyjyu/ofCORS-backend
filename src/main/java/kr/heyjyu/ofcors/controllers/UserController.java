package kr.heyjyu.ofcors.controllers;

import kr.heyjyu.ofcors.application.CountUserService;
import kr.heyjyu.ofcors.application.CreateUserService;
import kr.heyjyu.ofcors.application.EditProfileService;
import kr.heyjyu.ofcors.application.GetUserService;
import kr.heyjyu.ofcors.application.GetUsersService;
import kr.heyjyu.ofcors.dtos.ProfileDto;
import kr.heyjyu.ofcors.dtos.UserCountDto;
import kr.heyjyu.ofcors.dtos.UserCreationDto;
import kr.heyjyu.ofcors.dtos.UserDto;
import kr.heyjyu.ofcors.dtos.UserRegistrationDto;
import kr.heyjyu.ofcors.dtos.UsersDto;
import kr.heyjyu.ofcors.exceptions.SignUpFailed;
import kr.heyjyu.ofcors.models.DisplayName;
import kr.heyjyu.ofcors.models.Email;
import kr.heyjyu.ofcors.models.Password;
import kr.heyjyu.ofcors.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private GetUsersService getUsersService;
    private GetUserService getUserService;
    private CountUserService countUserService;
    private CreateUserService createUserService;
    private EditProfileService editProfileService;

    public UserController(GetUsersService getUsersService,
                          GetUserService getUserService,
                          CountUserService countUserService,
                          CreateUserService createUserService,
                          EditProfileService editProfileService) {
        this.getUsersService = getUsersService;
        this.getUserService = getUserService;
        this.countUserService = countUserService;
        this.createUserService = createUserService;
        this.editProfileService = editProfileService;
    }

    @GetMapping
    public UsersDto list(@RequestParam(required = false, defaultValue = "") String sort,
                         @RequestParam(required = false, defaultValue = "") String keyword,
                         @RequestParam(required = false, defaultValue = "30") Integer size) {
        return new UsersDto(getUsersService.getUsers(sort, keyword, size));
    }

    @GetMapping("{id}")
    public UserDto detail(@PathVariable Long id) {
        User user = getUserService.getUser(id);

        return user.toDto();
    }

    @GetMapping("me")
    public UserDto user(@RequestAttribute Long userId) {
        User user = getUserService.getUser(userId);

        return user.toDto();
    }

    @PatchMapping("me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void editProfile(@RequestAttribute Long userId,
                            @RequestBody ProfileDto profileDto) {
        editProfileService.editProfile(userId, profileDto);
    }

    @GetMapping("count")
    public UserCountDto userCount(@RequestParam String email) {
        return new UserCountDto(countUserService.count(new Email(email)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserCreationDto signUp(
            @RequestBody UserRegistrationDto userRegistrationDto
    ) {
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
