package com.example.login.repositories;

import com.example.login.model.Role;
import com.example.login.model.Usuario;
import com.example.login.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository repository;

    @Test
    void findByEmailTest_RetornaUsuario() {
        Usuario testUsuario = new Usuario(
                "user", "test", "test@gmail.com", "123", Role.ESTUDIANTE
        );
        Usuario user = repository.save(testUsuario);
        Optional<Usuario> userOption = repository.findByEmail(user.getEmail());

        assertTrue(userOption.isPresent(), "El usuario deberia de existir en db");
        Usuario userEncontrado = userOption.get();

        assertEquals("test@gmail.com", userEncontrado.getEmail(), "El email debe coincidir con el encontrado");

    }
}
