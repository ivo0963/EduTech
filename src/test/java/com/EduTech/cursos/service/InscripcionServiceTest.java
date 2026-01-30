package com.EduTech.cursos.service;

import com.EduTech.cursos.client.UsuarioClient;
import com.EduTech.cursos.dto.UsuarioDTO;
import com.EduTech.cursos.model.Curso;
import com.EduTech.cursos.model.Inscripcion;
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
    private UsuarioClient usuarioClient;

    @Mock
    private CursoRepository cursoRepository;

    @InjectMocks
    private InscripcionService inscripcionService;

    @Test
    void inscribir() {
        Long usuarioId = 1L;
        Long cursoId = 10L;

        Curso curso = new Curso();
        curso.setId(cursoId);

        Inscripcion inscripcionEsperada = new Inscripcion();
        inscripcionEsperada.setEstudianteId(usuarioId);

        when(usuarioClient.obtenerUsuario(usuarioId)).thenReturn(new UsuarioDTO());

        when(cursoRepository.findById(cursoId)).thenReturn(Optional.of(curso));

        when(inscripcionRepository.save(any(Inscripcion.class))).thenReturn(inscripcionEsperada);

        Inscripcion resultado = inscripcionService.inscribirUsuario(usuarioId, cursoId);

        assertNotNull(resultado);
        verify(usuarioClient).obtenerUsuario(usuarioId);
        verify(inscripcionRepository).save(any(Inscripcion.class));
    }
}