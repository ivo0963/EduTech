package com.EduTech.cursos.service;

import com.EduTech.cursos.model.Curso;
import com.EduTech.cursos.model.Inscripcion;
import com.EduTech.cursos.model.Usuario;
import com.EduTech.cursos.repository.CursoRepository;
import com.EduTech.cursos.repository.InscripcionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InscripcionServiceTest {

    @Mock
    private InscripcionRepository inscripcionRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private CursoRepository cursoRepository;

    @InjectMocks
    private InscripcionService inscripcionService;

    @Test
    void inscribir() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        Curso curso = new Curso();
        curso.setId(1L);
        Inscripcion inscripcion = new Inscripcion();

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));
        when(inscripcionRepository.save(any(Inscripcion.class))).thenReturn(inscripcion);

        Inscripcion resultado = inscripcionService.inscribir(1L, 1L);

        assertNotNull(resultado);
        verify(inscripcionRepository).save(any(Inscripcion.class));
    }
}