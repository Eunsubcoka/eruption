package com.eruption.eruption.auth.service;

import com.eruption.eruption.auth.dto.LoginRequest;
import com.eruption.eruption.auth.dto.LoginResponse;
import com.eruption.eruption.auth.dto.RefreshRequest;
import com.eruption.eruption.auth.repository.RefreshTokenStore;
import com.eruption.eruption.global.auth.JwtProvider;
import com.eruption.eruption.global.exception.BusinessException;
import com.eruption.eruption.global.exception.ErrorCode;
import com.eruption.eruption.user.entity.User;
import com.eruption.eruption.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final RefreshTokenStore refreshTokenStore;

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_CREDENTIALS));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BusinessException(ErrorCode.INVALID_CREDENTIALS);
        }

        return createTokens(user);
    }

    @Transactional(readOnly = true)
    public LoginResponse refresh(RefreshRequest request) {
        Long userId = jwtProvider.getRefreshUserId(request.refreshToken());

        String storedToken = refreshTokenStore.findByUserId(userId).orElseThrow(
                () -> new BusinessException(ErrorCode.INVALID_REFRESH_TOKEN));

        if (!storedToken.equals(request.refreshToken())) {
            refreshTokenStore.deleteByUserId(userId);
            throw new BusinessException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        User user = userRepository.findById(userId).orElseThrow(
                () -> new BusinessException(ErrorCode.INVALID_REFRESH_TOKEN)
        );

        return createTokens(user);

    }

    private LoginResponse createTokens(User user) {
        String accessToken = jwtProvider.createAccessToken(user.getId(), user.getRole());
        String refreshToken = jwtProvider.createRefreshToken(user.getId());

        refreshTokenStore.save(user.getId(), refreshToken, jwtProvider.getRefreshExpiration());

        return LoginResponse.of(accessToken, refreshToken);
    }

    public void logout(Long userId) {
        refreshTokenStore.deleteByUserId(userId);
    }
}
