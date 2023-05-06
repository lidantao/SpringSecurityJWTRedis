package com.lidantao.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Cola
 * @Date 2023年05月05日 11:20:00
 */
public class JWTUtils {

    public static String createJWT(String userId){
        Calendar expireData = Calendar.getInstance();
        expireData.add(Calendar.SECOND, 3600);

        String token = JWT.create()
                .withClaim("userId", userId)
                .withExpiresAt(expireData.getTime())
                .sign(Algorithm.HMAC256("lidantao"));

        return token;
    }

    public static Claim parseJWT(String token) {

        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("lidantao")).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        Claim claim = decodedJWT.getClaim("userId");
        return claim;
    }

}
