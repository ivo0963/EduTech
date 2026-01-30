package com.EduTech.cursos.service;

import com.EduTech.cursos.model.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void registrar() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Test User");
        usuario.setEmail("test@email.com");

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario resultado = usuarioService.registrar(usuario);

        assertNotNull(resultado);
        assertEquals("Test User", resultado.getNombre());
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void buscarPorEmail() {
        Usuario usuario = new Usuario();
        usuario.setEmail("test@email.com");

        when(usuarioRepository.findByEmail("test@email.com")).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.buscarPorEmail("test@email.com");

        assertNotNull(resultado);
        assertEquals("test@email.com", resultado.getEmail());
    }
}