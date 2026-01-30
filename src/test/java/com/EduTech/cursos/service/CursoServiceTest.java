package com.EduTech.cursos.service;

import com.EduTech.cursos.client.UsuarioClient;
import com.EduTech.cursos.dto.UsuarioDTO;
import com.EduTech.cursos.model.Curso;
import com.EduTech.cursos.repository.CursoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CursoServiceTest {

    @Mock
    private CursoRepository cursoRepository;

    @Mock
    private UsuarioClient usuarioClient;

    @InjectMocks
    private CursoService cursoService;

    @Test
    void crearCurso() {
        // 1. Datos
        Curso curso = new Curso();
        curso.setTitulo("Curso Java");
        Long instructorId = 1L;

        UsuarioDTO instructorMock = new UsuarioDTO();
        instructorMock.setId(instructorId);
        when(usuarioClient.obtenerUsuario(instructorId)).thenReturn(instructorMock);

        when(cursoRepository.save(any(Curso.class))).thenReturn(curso);

        Curso resultado = cursoService.crearCurso(curso, instructorId);

        assertNotNull(resultado);
        assertEquals(instructorId, resultado.getInstructorId());
        verify(usuarioClient).obtenerUsuario(instructorId);
        verify(cursoRepository).save(any(Curso.class));
    }

    @Test
    void listarCursos() {
        when(cursoRepository.findAll()).thenReturn(List.of(new Curso(), new Curso()));
        List<Curso> resultados = cursoService.listarCursos();
        assertEquals(2, resultados.size());
    }
}