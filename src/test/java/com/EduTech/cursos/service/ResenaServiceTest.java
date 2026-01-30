package com.EduTech.cursos.service;

import com.EduTech.cursos.model.Curso;
import com.EduTech.cursos.model.Usuario;
import com.EduTech.cursos.repository.CursoRepository;
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
class ResenaServiceTest {

    @Mock
    private ResenaRepository resenaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private CursoRepository cursoRepository;

    @InjectMocks
    private ResenaService resenaService;

    @Test
    void agregarResena() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        Curso curso = new Curso();
        curso.setId(1L);
        Resena resena = new Resena();
        resena.setComentario("Excelente");
        resena.setCalificacion(5);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));
        when(resenaRepository.save(any(Resena.class))).thenReturn(resena);

        Resena resultado = resenaService.agregarResena(1L, 1L, resena);

        assertNotNull(resultado);
        assertEquals(5, resultado.getCalificacion());
        verify(resenaRepository).save(any(Resena.class));
    }
}