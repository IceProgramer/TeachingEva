package com.itmo.teachingeva;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TeachingEvaApplicationTests {

    @Test
    void contextLoads() {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("EduEvaluationSystem")).build();
        DecodedJWT decodedJWT = jwtVerifier.verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IjEiLCJleHAiOjE2NzM2Mjc4MTcsImlhdCI6MTY3MzYyNjYxNywidXNlcm5hbWUiOiJhZG1pbiJ9.MZRy6frzjFurCjm95em3m8ydTbbjwjkYN97MxhDQw38");
        System.out.println(decodedJWT.getClaim("id").asString());
    }

}
