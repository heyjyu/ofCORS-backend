package kr.heyjyu.ofcors.controllers;

import jakarta.servlet.http.HttpServletResponse;
import kr.heyjyu.ofcors.application.ChargeService;
import kr.heyjyu.ofcors.dtos.ChargeRequestDto;
import kr.heyjyu.ofcors.dtos.KakaoPayApprovalDto;
import kr.heyjyu.ofcors.utils.KakaoPay;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("charges")
public class ChargeController {
    private ChargeService chargeService;
    private KakaoPay kakaoPay;

    public ChargeController(ChargeService chargeService, KakaoPay kakaoPay) {
        this.chargeService = chargeService;
        this.kakaoPay = kakaoPay;
    }

    @PostMapping
    public String charge(@RequestAttribute Long userId,
                       @RequestBody ChargeRequestDto chargeRequestDto,
                       HttpServletResponse response) {
        return chargeService.charge(userId, chargeRequestDto.getQuantity());
    }

    @GetMapping("/kakaoPaySuccess")
    public KakaoPayApprovalDto orderResult(
            @RequestParam("pg_token") String pgToken
    ) {
        return chargeService.completeCharge(kakaoPay.kakaoPayInfo(pgToken)).toDto();
    }
}
