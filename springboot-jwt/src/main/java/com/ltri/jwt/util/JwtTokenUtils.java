package com.ltri.jwt.util;

import com.ltri.jwt.entity.JwtUser;
import com.ltri.jwt.exception.BusinessException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;


@Slf4j
public class JwtTokenUtils {
    //密钥
    private static final String SECRET = "secret";
    //签发人
    private static final String ISS = "ltri";
    //过期时间(毫秒)
    private static final long EXPIRE_DATE = 30 * 60 * 1000;


    /**
     * 签发token
     */
    public static String generateToken(String username) {
        return Jwts.builder()
                .setIssuer(ISS)
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DATE))
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    /**
     * 通过token获取信息内容
     */
    public static Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * 判断过期情况
     */
    public static boolean isExpiration(String token) {
        return getClaimsFromToken(token).getExpiration().before(new Date());
    }

    /**
     * 从token中获取用户名
     */
    public static String getUsername(String token) {
        log.info("通过token获取用户名" + token);
        try {
            return getClaimsFromToken(token).getSubject();
        } catch (Exception e) {
            throw new BusinessException(500, "无效token");
        }
    }

    /**
     * 验证token，通过userDetails验证用户名以及token是否过期
     */
    public static Boolean validateToken(String token, UserDetails userDetails) {
        log.info("validateToken验证token" + token);
        log.info("验证userDetails" + userDetails);
        JwtUser user = (JwtUser) userDetails;
        String username = getUsername(token);
        return (username.equals(user.getUsername()) && !isExpiration(token));
    }
}

