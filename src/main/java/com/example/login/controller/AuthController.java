package com.example.login.controller;

import com.example.login.dto.LoginRequestDTO;
import com.example.login.model.Usuario;
import com.example.login.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO request) {
        try {
            Usuario user = authService.authenticate(request.getEmail(), request.getPassword());
            return ResponseEntity.ok(Map.of(
                    "message", "login exitoso",
                    "email", user.getEmail(),
                    "rol", user.getRol()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "message", e.getMessage(),
                    "error", "error en login"
            ));
        }

    }

}
