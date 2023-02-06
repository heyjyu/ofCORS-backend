package kr.heyjyu.ofcors.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class IamPortTest {
    @Autowired
    private IamPort iamPort;

    @Test
    void issueAccessToken() {
        assertThat(iamPort.issueAccessToken()).isNotNull();
    }
}
