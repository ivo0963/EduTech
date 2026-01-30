package com.EduTech.cursos.repository;

import com.EduTech.cursos.model.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {

    List<Inscripcion> findByEstudianteId(Long estudianteId);

    List<Inscripcion> findByCursoId(Long cursoId);
}