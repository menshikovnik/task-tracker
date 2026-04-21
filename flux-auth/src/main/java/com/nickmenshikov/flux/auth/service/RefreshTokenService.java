package com.nickmenshikov.flux.auth.service;

import com.nickmenshikov.flux.auth.repository.RefreshTokenRepository;
import com.nickmenshikov.flux.auth.repository.UserRepository;
import com.nickmenshikov.flux.core.exception.UnauthorizedException;
import com.nickmenshikov.flux.core.model.RefreshToken;
import com.nickmenshikov.flux.core.model.User;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Value("${jwt.refresh-expiration-ms}")
    private Duration refreshExpiration;

    @Transactional
    public RefreshToken create(Long userId) {
        return getRefreshToken(userId);
    }

    @Transactional
    public RefreshToken rotate(String tokenValue) {
        RefreshToken existing = refreshTokenRepository.findByToken(tokenValue).orElseThrow(
                () -> new UnauthorizedException("Refresh token not found")
        );

        if (existing.isExpired()) {
            refreshTokenRepository.deleteByToken(existing.getToken());
            throw new UnauthorizedException("Refresh token is expired");
        }

        refreshTokenRepository.deleteByToken(existing.getToken());

        return getRefreshToken(existing.getUser().getId());
    }

    @NonNull
    private RefreshToken getRefreshToken(Long userId) {
        User user = userRepository.getReferenceById(userId);

        RefreshToken newToken = new RefreshToken();
        newToken.setUser(user);
        newToken.setToken(UUID.randomUUID().toString());
        newToken.setExpiresAt(Instant.now().plusMillis(refreshExpiration.toMillis()));
        newToken.setCreatedAt(Instant.now());

        return refreshTokenRepository.save(newToken);
    }

    @Transactional
    public void revokeByToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }
}
