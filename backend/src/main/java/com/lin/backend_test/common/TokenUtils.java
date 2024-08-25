package com.lin.backend_test.common;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class TokenUtils {

    // 设置有效时长
    private static final long EXPIRE_TIME = 10 * 60 * 60 * 1000;

    private static final String TOKEN_SECRET = "develop-mode-secret";
    private static final String ISSUER = "dev";
    /**
     * 根据用户信息生成token
     *
     * @param tokenData 用户信息
     * @return token
     */
    public static String sign(TokenData tokenData) {
        String token = null;
        try {
            Date expiresAt = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            token = JWT.create()
                    .withIssuer(ISSUER)
                    .withClaim("userId", tokenData.userId)
                    .withClaim("userName", tokenData.userName)
                    .withClaim("role", tokenData.role)
                    .withExpiresAt(expiresAt)
                    .sign(Algorithm.HMAC256(TOKEN_SECRET));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }

    /**
     * 验证token是否有效
     * @param token token
     * @return 是否有效
     */
    public static boolean verify(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer(ISSUER).build();
            verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取token中的所有信息
     *
     * @param token token
     * @return token中包含的所有信息
     */
    public static TokenData getAllInfoFromToken(String token) {
        if (token == null || "".equals(token) || !verify(token)) {
            return null;
        }
        try {
            DecodedJWT jwt = JWT.decode(token);
            TokenData tokenData = new TokenData();
            tokenData.userId = jwt.getClaim("userId").asInt();
            tokenData.userName = jwt.getClaim("userName").asString();
            tokenData.role = jwt.getClaim("role").asString();
            return tokenData;
        } catch (JWTDecodeException e) {
            return null;
        }
    }
}
