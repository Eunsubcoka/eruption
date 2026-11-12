package com.eruption.eruption.auth.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RefreshTokenStore {

    private final StringRedisTemplate redisTemplate;
    private static final String PREFIX = "refresh:";

    public void save(Long userId, String refreshToken, Duration expiration){
        String key = PREFIX + userId;
        redisTemplate.opsForValue().set(key,refreshToken,expiration);
    }
    public Optional<String> findByUserId(Long userId){
        String key = PREFIX + userId;
        return Optional.ofNullable(redisTemplate.opsForValue().get(key));
    }

    public void deleteByUserId(Long userId){
        String key = PREFIX + userId;
        redisTemplate.delete(key);
    }

}
