package com.example.login.services;

import com.example.login.model.ResfreshToken;
import com.example.login.model.Usuario;
import com.example.login.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class RefreshTokenService {
    private Long refreshTokenDurationMs = 86400000L;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    public ResfreshToken createRefreshToken(Usuario usuario) {
        ResfreshToken refreshToken = new ResfreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setUsuario(usuario);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<ResfreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public void deleteByUsuario(Usuario usuario) {
        refreshTokenRepository.deleteByUsuario(usuario);
    }

    @Transactional
    public ResfreshToken rotateRefreshToken(ResfreshToken oldToken){
        refreshTokenRepository.delete(oldToken);
        return createRefreshToken(oldToken.getUsuario());
    }
    public void cleanUpExpiredTokens(){
        refreshTokenRepository.deleteExpiredTokens();
    }

    public boolean isTokenExpired(ResfreshToken token){
        return token.isExpired();
    }
}
