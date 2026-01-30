package com.EduTech.cursos.service;

import com.EduTech.cursos.client.UsuarioClient;
import com.EduTech.cursos.model.*;
import com.EduTech.cursos.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EvaluacionService {

    @Autowired private EvaluacionRepository evaluacionRepository;
    @Autowired private EntregaRepository entregaRepository;
    @Autowired private CursoRepository cursoRepository;

    @Autowired private UsuarioClient usuarioClient;

    public Evaluacion crearEvaluacion(Long idCurso, Evaluacion evaluacion) {
        Curso curso = cursoRepository.findById(idCurso)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));
        evaluacion.setCurso(curso);
        return evaluacionRepository.save(evaluacion);
    }

    public Evaluacion obtenerPorId(Long id) {
        return evaluacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evaluación no encontrada con ID: " + id));
    }

    public Entrega enviarRespuesta(Long idEvaluacion, Long idEstudiante, String respuestaTexto) {
        Evaluacion evaluacion = evaluacionRepository.findById(idEvaluacion)
                .orElseThrow(() -> new RuntimeException("Evaluación no encontrada"));

        usuarioClient.obtenerUsuario(idEstudiante);

        Entrega entrega = new Entrega();
        entrega.setEvaluacion(evaluacion);

        entrega.setEstudianteId(idEstudiante);

        entrega.setRespuestaAlumno(respuestaTexto);

        return entregaRepository.save(entrega);
    }

    public Entrega calificarEntrega(Long idEntrega, Double nota, String feedback) {
        Entrega entrega = entregaRepository.findById(idEntrega)
                .orElseThrow(() -> new RuntimeException("Entrega no encontrada"));

        entrega.setCalificacion(nota);
        entrega.setFeedbackProfesor(feedback);

        return entregaRepository.save(entrega);
    }

    public List<Evaluacion> listarPorCurso(Long idCurso) {
        return evaluacionRepository.findByCursoId(idCurso);
    }

    public List<Entrega> listarEntregasPorEvaluacion(Long idEvaluacion) {
        return entregaRepository.findByEvaluacionId(idEvaluacion);
    }
}