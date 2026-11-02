package com.eruption.eruption.global.auth;

import com.eruption.eruption.user.enums.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtProvider {
    private final SecretKey key;
    private final long accessExpirationMinutes;
    private final JwtParser jwtParser;

    public JwtProvider(@Value("${jwt.secret}") String secret,
                       @Value("${jwt.access-expiration}") long accessExpirationMinutes) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessExpirationMinutes = accessExpirationMinutes;
        this.jwtParser = Jwts.parser().verifyWith(key).build();
    }

    public String createAccessToken(Long userId, UserRole role) {
        Instant instant = Instant.now();
        return Jwts.builder()
                .subject(String.valueOf(userId))        // sub : 사용자 식별자
                .claim("role", role.name())             // 커스텀 클레임
                .issuedAt(Date.from(instant))                          // iat
                .expiration(Date.from(instant.plus(accessExpirationMinutes, ChronoUnit.MINUTES)))                     // exp
                .signWith(key)                          // 서명 (키 길이에 따라 알고리즘 자동 선택)
                .compact();
    }

    public Jws<Claims> parseClaims(String token) {
        return jwtParser.parseSignedClaims(token);
    }


    public Long getUserId(String token) {
        return Long.valueOf(parseClaims(token).getPayload().getSubject());
    }

    public UserRole getRole(String token) {
        return UserRole.valueOf(parseClaims(token).getPayload()
                .get("role", String.class));
    }

}

