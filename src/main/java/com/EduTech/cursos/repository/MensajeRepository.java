package com.EduTech.cursos.repository;

import com.EduTech.cursos.model.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Long> {

    @Query("SELECT m FROM Mensaje m WHERE " +
            "(m.remitente.id = :usuario1 AND m.destinatario.id = :usuario2) OR " +
            "(m.remitente.id = :usuario2 AND m.destinatario.id = :usuario1) " +
            "ORDER BY m.fechaEnvio ASC")
    List<Mensaje> obtenerConversacion(@Param("usuario1") Long usuario1, @Param("usuario2") Long usuario2);
}