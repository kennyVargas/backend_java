package com.example.login.controller;

import com.example.login.dto.LoginRequestDTO;
import com.example.login.model.Usuario;
import com.example.login.services.AuthService;
import com.example.login.services.JwtService;
import com.example.login.utils.AuthResponseBuilder;
import com.example.login.utils.ErrorResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;
    private JwtService jwtService;

    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO request) {
        try {
            Usuario user = authService.authenticate(request.getEmail(), request.getPassword());
            Map<String, Object> extraClaims = AuthResponseBuilder.buildUser(user);
            String jwtToken = jwtService.generateToken(extraClaims, user);
            return ResponseEntity.ok(AuthResponseBuilder.buildAuthResponse(jwtToken, user));
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
