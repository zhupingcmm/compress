package com.common.utils;

import com.common.base.Constants;
import com.common.base.ResponseEnum;
import com.common.exception.CompressException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

public class JwtUtil {
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public static String createToken(String id, String username, String password, Long tokenExpireTime) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(Constants.USERNAME, username);
        map.put(Constants.PASSWORD, password);

        return Jwts.builder()
                .setId(id)
                .setSubject(username)
                .addClaims(map)
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpireTime))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public static Claims parseToken(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (Exception e) {
            throw new CompressException(ResponseEnum.TOKEN_EXPIRED);
        }
    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        } catch (ExpiredJwtException | MalformedJwtException | SignatureException e) {
            if ( e instanceof ExpiredJwtException) {
                throw new CompressException(ResponseEnum.TOKEN_EXPIRED);
            }
            return false;
        }
        return true;
    }
}
