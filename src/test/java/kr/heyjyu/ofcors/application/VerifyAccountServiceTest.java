package kr.heyjyu.ofcors.application;

import kr.heyjyu.ofcors.models.AccountNumber;
import kr.heyjyu.ofcors.models.Bank;
import kr.heyjyu.ofcors.models.User;
import kr.heyjyu.ofcors.repositories.UserRepository;
import kr.heyjyu.ofcors.utils.IamPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@SpringBootTest
@ActiveProfiles("test")
class VerifyAccountServiceTest {
    private VerifyAccountService verifyAccountService;
    private UserRepository userRepository;
    @MockBean
    private IamPort iamPort;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        verifyAccountService = new VerifyAccountService(userRepository, iamPort);
    }

    @Test
    void verify() {
        given(userRepository.findById(any()))
                .willReturn(Optional.of(User.fake()));

        given(iamPort.getBankHolder(any(), any()))
                .willReturn("홍길동");

        assertThat(verifyAccountService.verify(1L, new Bank("우리은행"), new AccountNumber("11111111"))).isNotNull();
    }
}