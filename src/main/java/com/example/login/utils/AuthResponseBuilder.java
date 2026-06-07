package com.example.login.utils;

import com.example.login.model.Usuario;

import java.util.HashMap;
import java.util.Map;

public class AuthResponseBuilder {
    public static Map<String, Object> buildAuthResponse(String token, String reftoken, Usuario usuario) {
        Map<String, Object> authResponse = new HashMap<>();
        authResponse.put("token", token);
        authResponse.put("refreshToken", reftoken);
        authResponse.put("tokenType", "Bearer");
        authResponse.put("expiresIn", 3600);
        authResponse.put("usuario", buildUserResponse(usuario));
        return authResponse;
    }

    private static Map<String, Object> buildUserResponse(Usuario usuario) {
        Map<String, Object> userResponse = new HashMap<>();
        userResponse.put("id", usuario.getId());
        userResponse.put("email", usuario.getEmail());
        userResponse.put("rol", usuario.getRol());
        userResponse.put("name", usuario.getNombre());
        userResponse.put("apellido", usuario.getApellido());
        return userResponse;
    }

    public static Map<String, Object> buildUser(Usuario usuario) {
        Map<String, Object> user = new HashMap<>();
        user.put("rol", usuario.getRol());
        user.put("nombre", usuario.getNombre());
        user.put("email", usuario.getEmail());
        return user;
    }
}
