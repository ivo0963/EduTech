package com.EduTech.cursos.repository;

import com.EduTech.cursos.model.Entrega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EntregaRepository extends JpaRepository<Entrega, Long> {

    List<Entrega> findByEstudianteId(Long estudianteId);

    List<Entrega> findByEvaluacionId(Long evaluacionId);
}