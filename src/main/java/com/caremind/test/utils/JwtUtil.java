package com.caremind.test.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    private String TEST_KEY = "TESTKEY";
    private Date EXPIRED_TIME = new Date(System.currentTimeMillis() + 1000 * 10);
    private String ISSURE = "KYH";

    public String createToken(){
        return JWT.create()
                .withIssuer(ISSURE)
                .withExpiresAt(EXPIRED_TIME)
                .sign(Algorithm.HMAC256(TEST_KEY));
    }

    public void verifyToken(String givenToken){
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TEST_KEY))
                .withIssuer(ISSURE)
                .build();

        verifier.verify(givenToken);
    }
}
