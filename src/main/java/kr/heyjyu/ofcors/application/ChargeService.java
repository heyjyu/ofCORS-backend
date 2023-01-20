package kr.heyjyu.ofcors.application;

import jakarta.transaction.Transactional;
import kr.heyjyu.ofcors.models.Charge;
import kr.heyjyu.ofcors.models.KakaoPayApproval;
import kr.heyjyu.ofcors.models.Price;
import kr.heyjyu.ofcors.models.Quantity;
import kr.heyjyu.ofcors.models.UserId;
import kr.heyjyu.ofcors.repositories.ChargeRepository;
import kr.heyjyu.ofcors.utils.KakaoPay;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
public class ChargeService {
    private ChargeRepository chargeRepository;
    private KakaoPay kakaoPay;

    public ChargeService(ChargeRepository chargeRepository, KakaoPay kakaoPay) {
        this.chargeRepository = chargeRepository;
        this.kakaoPay = kakaoPay;
    }

    public String charge(Long userId, Long quantity) {
        String orderId = UUID.randomUUID().toString();

        return kakaoPay.kakaoPayReady(
                orderId,
                userId,
                "ofCORS 포인트",
                quantity,
                quantity * 110
        );
    }

    public KakaoPayApproval completeCharge(KakaoPayApproval kakaoPayApproval) {
        UserId userId = new UserId(Long.valueOf(kakaoPayApproval.getPartner_user_id()));
        Quantity quantity = new Quantity(Long.valueOf(kakaoPayApproval.getQuantity()));
        Price price = new Price(110L);

        Charge charge = new Charge(userId, quantity, price);
        chargeRepository.save(charge);

        return kakaoPayApproval;
    }
}
