package com.example.login.services;

import com.example.login.model.Role;
import com.example.login.model.Usuario;
import com.example.login.repository.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class Data {
    /*
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init(){
        Usuario admin = new Usuario();
        admin.setEmail("test@gmail.com");
        admin.setPassword(passwordEncoder.encode("123"));
        admin.setRol(Role.ADMIN);
        admin.setNombre("test");
        admin.setApellido("admin");
        usuarioRepository.save(admin);
    }
    */
}
