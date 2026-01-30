package com.EduTech.cursos.service;

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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CursoServiceTest {

    @Mock
    private CursoRepository cursoRepository;

    @InjectMocks
    private CursoService cursoService;

    @Test
    void crearCurso() {
        Curso curso = new Curso();
        curso.setTitulo("Curso Java");
        Long instructorId = 1L;

        when(cursoRepository.save(any(Curso.class))).thenReturn(curso);

        Curso resultado = cursoService.crearCurso(curso, instructorId);

        assertNotNull(resultado);
        assertEquals(instructorId, resultado.getInstructorId());
        verify(cursoRepository).save(any(Curso.class));
    }

    @Test
    void listarCursos() {
        when(cursoRepository.findAll()).thenReturn(List.of(new Curso(), new Curso()));

        List<Curso> resultados = cursoService.listarCursos();

        assertEquals(2, resultados.size());
        verify(cursoRepository).findAll();
    }

    @Test
    void obtenerPorId() {
        Curso curso = new Curso();
        curso.setId(1L);
        when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));

        Curso resultado = cursoService.obtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }
}