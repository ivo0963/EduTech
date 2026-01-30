package com.EduTech.cursos.service;

import com.EduTech.cursos.client.UsuarioClient;
import com.EduTech.cursos.model.Entrega;
import com.EduTech.cursos.model.Evaluacion;
import com.EduTech.cursos.repository.EntregaRepository;
import com.EduTech.cursos.repository.EvaluacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EntregaService {

    @Autowired
    private EntregaRepository entregaRepository;

    @Autowired
    private EvaluacionRepository evaluacionRepository;

    @Autowired
    private UsuarioClient usuarioClient;

    public Entrega realizarEntrega(Long evaluacionId, Long estudianteId, String respuesta) {
        usuarioClient.obtenerUsuario(estudianteId);

        Evaluacion evaluacion = evaluacionRepository.findById(evaluacionId)
                .orElseThrow(() -> new RuntimeException("Evaluación no encontrada"));

        Entrega entrega = new Entrega();
        entrega.setEvaluacion(evaluacion);
        entrega.setEstudianteId(estudianteId);
        entrega.setRespuestaAlumno(respuesta);

        return entregaRepository.save(entrega);
    }

    public Entrega calificarEntrega(Long entregaId, Double nota, String feedback) {
        Entrega entrega = entregaRepository.findById(entregaId)
                .orElseThrow(() -> new RuntimeException("Entrega no encontrada"));

        entrega.setCalificacion(nota);
        entrega.setFeedbackProfesor(feedback);

        return entregaRepository.save(entrega);
    }

    public List<Entrega> listarPorEvaluacion(Long evaluacionId) {
        return entregaRepository.findByEvaluacionId(evaluacionId);
    }

    public List<Entrega> listarPorEstudiante(Long estudianteId) {
        return entregaRepository.findByEstudianteId(estudianteId);
    }
}