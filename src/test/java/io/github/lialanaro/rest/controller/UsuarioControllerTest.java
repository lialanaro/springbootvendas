package io.github.lialanaro.rest.controller;

import io.github.lialanaro.domain.entity.Usuario;
import io.github.lialanaro.domain.repository.UsuarioRepository;
import io.github.lialanaro.rest.dto.CredenciaisDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class UsuarioControllerTest {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UsuarioController controller;
    @Autowired
    UsuarioRepository repository;

    @BeforeAll
    void dados(){
        Usuario usuario = new Usuario();
        usuario.setLogin("liazinha");
        usuario.setAdmin(false);
        usuario.setSenha(passwordEncoder.encode("123"));
        repository.save(usuario);
    }

    @Test
    void salvar() {
        Usuario usuario1 = new Usuario();
        usuario1.setLogin("Anderson");
        usuario1.setSenha("123");
        controller.salvar(usuario1);
        assertEquals(2, usuario1.getId());

    }

    @Test
    void autenticar() {
        CredenciaisDto dto = new CredenciaisDto();
        dto.setLogin("liazinha");
        dto.setSenha("123");
        assertDoesNotThrow(()->controller.autenticar(dto));
        dto.setSenha("321");
        assertThrows(ResponseStatusException.class,()-> controller.autenticar(dto));
        dto.setLogin("Ander");
        assertThrows(ResponseStatusException.class,()-> controller.autenticar(dto));

    }
}