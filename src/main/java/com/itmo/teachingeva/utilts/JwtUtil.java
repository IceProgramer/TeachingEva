package com.itmo.teachingeva.utilts;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.itmo.teachingeva.common.CustomException;
import com.itmo.teachingeva.entity.Admin;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static java.security.KeyRep.Type.SECRET;

public class JwtUtils {

    private static final long TIME = 60 * 60 * 1000;     //设置过期时间
    private static final String SIGNATURE = "EduEvaluationSystem";

    /**
     * 创建token
     *
     * @param admin 管理员相关信息
     * @return token值
     */
    public static String createToken(Admin admin) {
        if (admin == null) {
            throw new CustomException("用户为空");
        }
        String jwtToken = Jwts.builder()
                //header
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                //payload
                .claim("username", admin.getUsername())
                .claim("password", admin.getPassword())
                .setSubject("admin-evaluation")
                // 设置过期时间
                .setExpiration(new Date(System.currentTimeMillis() + TIME))
                .setId(admin.getId().toString())
                //signature
                .signWith(SignatureAlgorithm.HS256, SIGNATURE)
                .compact();
        return jwtToken;
    }


    /**
     * 认证token
     * @param token token值
     * @return 认证是否成功
     */
    public static boolean verify(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SIGNATURE)).withIssuer("auth0").build();
            DecodedJWT jwt = verifier.verify(token);
            System.out.println("认证通过：");
            System.out.println("过期时间：" + jwt.getExpiresAt());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Token认证失败，需要重新登录！");
        }
        return false;
    }

}
