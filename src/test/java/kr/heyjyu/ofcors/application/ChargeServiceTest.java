package kr.heyjyu.ofcors.application;

import kr.heyjyu.ofcors.models.KakaoPayApproval;
import kr.heyjyu.ofcors.models.Points;
import kr.heyjyu.ofcors.models.User;
import kr.heyjyu.ofcors.repositories.ChargeRepository;
import kr.heyjyu.ofcors.repositories.UserRepository;
import kr.heyjyu.ofcors.utils.KakaoPay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
class ChargeServiceTest {
    private ChargeRepository chargeRepository;
    private UserRepository userRepository;
    private ChargeService chargeService;
    @Autowired
    private KakaoPay kakaoPay;

    @BeforeEach
    void setup() {
        chargeRepository = mock(ChargeRepository.class);
        userRepository = mock(UserRepository.class);
        chargeService = new ChargeService(chargeRepository, userRepository, kakaoPay);
    }

    @Test
    void charge() {
        String kakaoPayUrl = chargeService.charge(1L, 30L);

        assertThat(kakaoPayUrl).isNotBlank();
    }

    @Test
    void completeCharge() {
        User user = User.fake();

        given(userRepository.findById(any()))
                .willReturn(Optional.of(user));

        Points initialPoints = user.getPoints();

        chargeService.completeCharge(KakaoPayApproval.fake());

        assertThat(user.getPoints()).isEqualTo(initialPoints.add(new Points(1L)));

        verify(chargeRepository).save(any());
    }
}
