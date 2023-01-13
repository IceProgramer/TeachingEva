package com.itmo.teachingeva.utilts;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.itmo.teachingeva.common.CustomException;
import com.itmo.teachingeva.entity.Admin;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class JwtUtil {

    private static final long TIME = 60 * 60 * 1000;     //设置过期时间
    private static final String SIGNATURE = "EduEvaluationSystem";

//    /**
//     * 创建token
//     *
//     * @param admin 管理员相关信息
//     * @return token值
//     */
//    public static String createToken(Admin admin) {
//
//
//        if (admin == null) {
//            throw new CustomException("用户为空");
//        }
//        String jwtToken = Jwts.builder()
//                //header
//                .setHeaderParam("typ", "JWT")
//                .setHeaderParam("alg", "HS256")
//                //payload
//                .setIssuer("auth0")
//                .claim("username", admin.getUsername())
//                .claim("password", admin.getPassword())
//                // 设置过期时间
//                .setExpiration(new Date(System.currentTimeMillis() + TIME))
//                .setId(admin.getId().toString())
//                //signature
//                .signWith(SignatureAlgorithm.HS256, SIGNATURE)
//                .compact();
//        return jwtToken;
//    }
//
//
//    /**
//     * 认证token
//     *
//     * @param token token值
//     * @return 认证是否成功
//     */
//    public static DecodedJWT verify(String token) {
//        try {
//            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SIGNATURE)).withIssuer("auth0").build();
//            DecodedJWT jwt = verifier.verify(token);
//            log.info("认证通过");
//            log.info("过期时间：{}", jwt.getExpiresAt());
//            System.out.println("过期时间：" + jwt.getExpiresAt());
//            return jwt;
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("Token认证失败，需要重新登录！");
//        }
//        throw new CustomException("认证失败");
//    }

    /**
     * 生成token
     * @param payload token携带的信息
     * @return token字符串
     */
    public static String generateToken(Map<String,String> payload){
        //1.头部默认
        // 指定token过期时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 10);  //10s
        JWTCreator.Builder builder = JWT.create();
        // 2.构建payload
        payload.forEach(builder::withClaim);
        //3.构建签证
        // 指定签发时间、过期时间 和 签名算法，并返回token
        String token = builder.withIssuedAt(new Date()).withExpiresAt(calendar.getTime()).sign(Algorithm.HMAC256(SIGNATURE));
        return token;
    }


    /**
     * 解析token
     * @param token token字符串
     * @return 解析后的token类
     */
    public static DecodedJWT decodeToken(String token){
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SIGNATURE)).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);

        return decodedJWT;
    }

}
