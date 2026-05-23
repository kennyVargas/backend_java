package com.example.login.services;

import com.example.login.model.Usuario;
import com.example.login.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario authenticate(String email, String pass) {
        Usuario user = usuarioRepository.findByEmail(email).
                orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if(!passwordEncoder.matches(pass,user.getPassword())){
            throw  new RuntimeException("Contraseña incorrecta");
        }
        return user;
    }

}
