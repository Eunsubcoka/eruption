package com.eruption.eruption.global.auth;

import com.eruption.eruption.global.auth.enums.TokenType;
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
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtProvider {
    private final SecretKey key;
    private final long accessExpirationMinutes;
    private final long refreshExpirationDays;
    private final JwtParser jwtAccessParser;
    private final JwtParser jwtRefreshParser;

    private static final String TYPE_CLAIM = "type";
    private static final String ROLE_CLAIM = "role";

    public JwtProvider(@Value("${jwt.secret}") String secret,
                       @Value("${jwt.access-expiration-minutes}") long accessExpirationMinutes,
                       @Value("${jwt.refresh-expiration-days}") long refreshExpirationDays) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessExpirationMinutes = accessExpirationMinutes;
        this.refreshExpirationDays = refreshExpirationDays;
        this.jwtAccessParser = Jwts.parser().verifyWith(key).require(TYPE_CLAIM, TokenType.ACCESS.name()).build();
        this.jwtRefreshParser = Jwts.parser().verifyWith(key).require(TYPE_CLAIM, TokenType.REFRESH.name()).build();
    }

    public String createAccessToken(Long userId, UserRole userRole) {
        Instant instant = Instant.now();
        return Jwts.builder()
                .subject(String.valueOf(userId))        // sub : 사용자 식별자
                .claim(ROLE_CLAIM, userRole.name())
                .claim(TYPE_CLAIM, TokenType.ACCESS.name())
                .issuedAt(Date.from(instant))                          // iat
                .expiration(Date.from(instant.plus(accessExpirationMinutes, ChronoUnit.MINUTES)))                     // exp
                .signWith(key)                          // 서명 (키 길이에 따라 알고리즘 자동 선택)
                .compact();
    }

    public String createRefreshToken(Long userId) {
        Instant instant = Instant.now();
        return Jwts.builder()
                .subject(String.valueOf(userId))        // sub : 사용자 식별자
                .claim(TYPE_CLAIM, TokenType.REFRESH.name())
                .id(UUID.randomUUID().toString())
                .issuedAt(Date.from(instant))                          // iat
                .expiration(Date.from(instant.plus(refreshExpirationDays, ChronoUnit.DAYS)))                     // exp
                .signWith(key)                          // 서명 (키 길이에 따라 알고리즘 자동 선택)
                .compact();
    }

    private Jws<Claims> parseAccessClaims(String token) {
        return jwtAccessParser.parseSignedClaims(token);
    }


    public Long getAccessUserId(String token) {
        return Long.valueOf(parseAccessClaims(token).getPayload().getSubject());
    }

    public UserRole getAccessRole(String token) {
        return UserRole.valueOf(parseAccessClaims(token).getPayload()
                .get(ROLE_CLAIM, String.class));
    }

    private Jws<Claims> parseRefreshClaims(String token) {
        return jwtRefreshParser.parseSignedClaims(token);
    }

    public Long getRefreshUserId(String token) {
        return Long.valueOf(parseRefreshClaims(token).getPayload().getSubject());
    }
    public Duration getRefreshExpiration() {
        return Duration.ofDays(refreshExpirationDays);
    }

}

