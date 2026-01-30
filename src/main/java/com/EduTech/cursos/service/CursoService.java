package com.EduTech.cursos.service;

import com.EduTech.cursos.client.UsuarioClient;
import com.EduTech.cursos.dto.UsuarioDTO;
import com.EduTech.cursos.model.Curso;
import com.EduTech.cursos.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioClient usuarioClient;

    public Curso guardarCurso(Curso curso) {
        if (curso.getEstado() == null || curso.getEstado().isEmpty()) {
            curso.setEstado("PENDIENTE");
        }
        return cursoRepository.save(curso);
    }

    public Curso crearCurso(Curso curso, Long instructorId) {
        UsuarioDTO instructor = usuarioClient.obtenerUsuario(instructorId);
        curso.setInstructorId(instructorId);
        if (curso.getEstado() == null || curso.getEstado().isEmpty()) {
            curso.setEstado("PENDIENTE");
        }
        return cursoRepository.save(curso);
    }

    public List<Curso> listarCursos() {
        return cursoRepository.findAll();
    }

    public Optional<Curso> obtenerPorId(Long id) {
        return cursoRepository.findById(id);
    }

    public List<Curso> listarCursosPorInstructor(Long idInstructor) {
        return cursoRepository.findByInstructorId(idInstructor);
    }

    public Curso aprobarCurso(Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));
        curso.setEstado("APROBADO");
        return cursoRepository.save(curso);
    }

    public Curso asignarInstructor(Long cursoId, Long instructorId) {
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        UsuarioDTO instructor = usuarioClient.obtenerUsuario(instructorId); // Valida que exista el usuario
        curso.setInstructorId(instructorId);

        return cursoRepository.save(curso);
    }

    public void eliminarCurso(Long id) {
        cursoRepository.deleteById(id);
    }
}