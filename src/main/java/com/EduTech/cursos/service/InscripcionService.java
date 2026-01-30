package com.EduTech.cursos.service;

import com.EduTech.cursos.client.UsuarioClient;
import com.EduTech.cursos.model.Curso;
import com.EduTech.cursos.model.Inscripcion;
import com.EduTech.cursos.repository.CursoRepository;
import com.EduTech.cursos.repository.InscripcionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InscripcionService {

    @Autowired
    private InscripcionRepository inscripcionRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioClient usuarioClient;

    public Inscripcion inscribirUsuario(Long usuarioId, Long cursoId) {
        usuarioClient.obtenerUsuario(usuarioId);

        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setEstudianteId(usuarioId);
        inscripcion.setCurso(curso);
        inscripcion.setFechaInscripcion(java.time.LocalDate.now());

        return inscripcionRepository.save(inscripcion);
    }

    public List<Inscripcion> obtenerInscripcionesPorUsuario(Long usuarioId) {
        return inscripcionRepository.findByEstudianteId(usuarioId);
    }


    public Inscripcion obtenerPorId(Long id) {
        return inscripcionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inscripción no encontrada"));
    }

    public List<Inscripcion> obtenerInscripcionesPorCurso(Long cursoId) {
        return inscripcionRepository.findByCursoId(cursoId);
    }

    public Inscripcion actualizarProgreso(Long idInscripcion, Double porcentaje) {
        Inscripcion inscripcion = obtenerPorId(idInscripcion);
        inscripcion.setProgreso(porcentaje);
        return inscripcionRepository.save(inscripcion);
    }
}