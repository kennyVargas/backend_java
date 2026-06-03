package com.example.login.repository;

import com.example.login.model.ResfreshToken;
import com.example.login.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<ResfreshToken, Long> {
    Optional<ResfreshToken> findByToken(String token);
    Optional<ResfreshToken> findByUsuario(Usuario usuario);

    @Modifying
    @Query("DELETE FROM ResfreshToken rt WHERE rt.usuario = :usuario")
    void deleteByUsuario(@Param("usuario") Usuario usuario);

    @Modifying
    @Query("DELETE FROM ResfreshToken rt WHERE rt.expiryDate < CURRENT_TIMESTAMP")
    void deleteExpiredTokens();
}
