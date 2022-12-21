package kr.heyjyu.ofcors.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {
    @Test
    void encodeAndDecode() {
        JwtUtil jwtUtil = new JwtUtil("SECRET");

        String token = jwtUtil.encode(1L);

        assertThat(jwtUtil.decode(token)).isEqualTo(1L);
    }
}
