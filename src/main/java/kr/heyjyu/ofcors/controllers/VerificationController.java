package kr.heyjyu.ofcors.controllers;

import kr.heyjyu.ofcors.application.VerifyAccountService;
import kr.heyjyu.ofcors.dtos.AccountVerificationRequestDto;
import kr.heyjyu.ofcors.dtos.AccountVerificationResultDto;
import kr.heyjyu.ofcors.dtos.AdoptRequestDto;
import kr.heyjyu.ofcors.dtos.QuestionCreationDto;
import kr.heyjyu.ofcors.dtos.QuestionDto;
import kr.heyjyu.ofcors.dtos.QuestionModificationDto;
import kr.heyjyu.ofcors.dtos.QuestionRequestDto;
import kr.heyjyu.ofcors.models.AccountNumber;
import kr.heyjyu.ofcors.models.AnswerId;
import kr.heyjyu.ofcors.models.Bank;
import kr.heyjyu.ofcors.models.Body;
import kr.heyjyu.ofcors.models.Points;
import kr.heyjyu.ofcors.models.Question;
import kr.heyjyu.ofcors.models.QuestionId;
import kr.heyjyu.ofcors.models.Tag;
import kr.heyjyu.ofcors.models.Title;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class VerificationController {
    private VerifyAccountService verifyAccountService;

    public VerificationController(VerifyAccountService verifyAccountService) {
        this.verifyAccountService = verifyAccountService;
    }

    @PostMapping("/account/verify")
    public AccountVerificationResultDto verifyAccount(@RequestAttribute Long userId, @RequestBody AccountVerificationRequestDto accountVerificationRequestDto) {
        Bank bank = new Bank(accountVerificationRequestDto.getBank());
        AccountNumber accountNumber = new AccountNumber(accountVerificationRequestDto.getAccountNumber());

        return verifyAccountService.verify(userId, bank, accountNumber);
    }
}
