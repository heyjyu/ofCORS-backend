package kr.heyjyu.ofcors.controllers;

import kr.heyjyu.ofcors.application.LoginService;
import kr.heyjyu.ofcors.application.TrialLoginService;
import kr.heyjyu.ofcors.dtos.LoginRequestDto;
import kr.heyjyu.ofcors.dtos.LoginResultDto;
import kr.heyjyu.ofcors.exceptions.LoginFailed;
import kr.heyjyu.ofcors.models.Email;
import kr.heyjyu.ofcors.models.Password;
import kr.heyjyu.ofcors.models.User;
import kr.heyjyu.ofcors.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("session")
public class SessionController {
    private LoginService loginService;
    private TrialLoginService trialLoginService;
    private JwtUtil jwtUtil;

    public SessionController(LoginService loginService, TrialLoginService trialLoginService, JwtUtil jwtUtil) {
        this.loginService = loginService;
        this.trialLoginService = trialLoginService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LoginResultDto login(@RequestBody LoginRequestDto loginRequestDto) {
        User user = loginService.login(new Email(loginRequestDto.getEmail()), new Password(loginRequestDto.getPassword()));

        String accessToken = jwtUtil.encode(user.getId());

        return new LoginResultDto(accessToken);
    }

    @PostMapping("trial")
    @ResponseStatus(HttpStatus.CREATED)
    public LoginResultDto trialLogin() {
        User user = trialLoginService.login();

        String accessToken = jwtUtil.encode(user.getId());

        return new LoginResultDto(accessToken);
    }

    @ExceptionHandler(LoginFailed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String loginFailed() {
        return "Login failed!";
    }
}
