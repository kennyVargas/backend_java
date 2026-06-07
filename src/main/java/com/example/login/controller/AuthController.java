package com.example.login.controller;

import com.example.login.dto.LoginRequestDTO;
import com.example.login.dto.RefreshTokenRequest;
import com.example.login.model.ResfreshToken;
import com.example.login.model.Usuario;
import com.example.login.services.AuthService;
import com.example.login.services.JwtService;
import com.example.login.services.RefreshTokenService;
import com.example.login.utils.AuthResponseBuilder;
import com.example.login.utils.ErrorResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;
    private JwtService jwtService;
    private RefreshTokenService refreshTokenService;

    public AuthController(AuthService authService, JwtService jwtService, RefreshTokenService refreshTokenService) {
        this.authService = authService;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO request) {
        try {
            Usuario user = authService.authenticate(request.getEmail(), request.getPassword());
            refreshTokenService.deleteByUsuario(user);

            Map<String, Object> userClaims = AuthResponseBuilder.buildUser(user);
            String jwtToken = jwtService.generateToken(userClaims, user);
            ResfreshToken resfreshToken = refreshTokenService.createRefreshToken(user);

            return ResponseEntity.ok(AuthResponseBuilder.buildAuthResponse(jwtToken, resfreshToken.getToken(), user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ErrorResponseBuilder.buildErrorResponse(e.getMessage(), HttpStatus.UNAUTHORIZED));
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        try {
            ResfreshToken resfreshToken = refreshTokenService.findByToken(request.getRefreshToken())
                    .orElseThrow(() -> new RuntimeException("Refresh Token no valido"));

            if (refreshTokenService.isTokenExpired(resfreshToken)) {
                refreshTokenService.deleteByUsuario(resfreshToken.getUsuario());
                throw new RuntimeException("Refresh token ha expirado");
            }
            ResfreshToken newRefrestoken = refreshTokenService.rotateRefreshToken(resfreshToken);

            Map<String, Object> extraClaims = new HashMap<>();
            extraClaims.put("rol", newRefrestoken.getUsuario().getRol().name());
            extraClaims.put("nombre", newRefrestoken.getUsuario().getNombre());
            String jwtToken = jwtService.generateToken(extraClaims, newRefrestoken.getUsuario());

            return ResponseEntity.ok(AuthResponseBuilder.buildAuthResponse(jwtToken, newRefrestoken.getToken(), resfreshToken.getUsuario()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ErrorResponseBuilder.buildErrorResponse(e.getMessage(), HttpStatus.UNAUTHORIZED));
        }
    }

    @GetMapping("/v1")
    public String saludo() {
        System.out.println("hola");
        return "hola perro";
    }

}
