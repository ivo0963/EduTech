package com.EduTech.cursos.service;

import com.EduTech.cursos.model.Curso;
import com.EduTech.cursos.model.Evaluacion;
import com.EduTech.cursos.repository.CursoRepository;
import com.EduTech.cursos.repository.EvaluacionRepository;
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
class EvaluacionServiceTest {

    @Mock
    private EvaluacionRepository evaluacionRepository;

    @Mock
    private CursoRepository cursoRepository;

    @InjectMocks
    private EvaluacionService evaluacionService;

    @Test
    void crearEvaluacion() {
        Curso curso = new Curso();
        curso.setId(1L);
        Evaluacion evaluacion = new Evaluacion();
        evaluacion.setTitulo("Examen");

        when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));
        when(evaluacionRepository.save(any(Evaluacion.class))).thenReturn(evaluacion);

        Evaluacion resultado = evaluacionService.crearEvaluacion(1L, evaluacion);

        assertNotNull(resultado);
        assertEquals("Examen", resultado.getTitulo());
        verify(evaluacionRepository).save(any(Evaluacion.class));
    }
}