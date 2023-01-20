package kr.heyjyu.ofcors.application;

import kr.heyjyu.ofcors.models.KakaoPayApproval;
import kr.heyjyu.ofcors.repositories.ChargeRepository;
import kr.heyjyu.ofcors.utils.KakaoPay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
class ChargeServiceTest {
    private ChargeRepository chargeRepository;
    private ChargeService chargeService;
    @Autowired
    private KakaoPay kakaoPay;

    @BeforeEach
    void setup() {
        chargeRepository = mock(ChargeRepository.class);
        chargeService = new ChargeService(chargeRepository, kakaoPay);
    }

    @Test
    void charge() {
        String kakaoPayUrl = chargeService.charge(1L, 30L);

        assertThat(kakaoPayUrl).isNotBlank();
    }

    @Test
    void completeCharge() {
        chargeService.completeCharge(KakaoPayApproval.fake());

        verify(chargeRepository).save(any());
    }
}
