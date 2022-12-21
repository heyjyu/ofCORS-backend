package kr.heyjyu.ofcors.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;

public class JwtUtil {
    private Algorithm algorithm;

    public JwtUtil(String secret) {
        algorithm = Algorithm.HMAC256(secret);
    }

    public String encode(Long id) {
        return JWT.create()
                .withClaim("userId", id)
                .sign(algorithm);
    }

    public Long decode(String token) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token).getClaim("userId").asLong();
    }
}
