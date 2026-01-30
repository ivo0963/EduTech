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
            "(m.remitenteId = :usuario1Id AND m.destinatarioId = :usuario2Id) OR " +
            "(m.remitenteId = :usuario2Id AND m.destinatarioId = :usuario1Id) " +
            "ORDER BY m.fechaEnvio ASC")
    List<Mensaje> obtenerConversacion(@Param("usuario1Id") Long usuario1Id,
                                      @Param("usuario2Id") Long usuario2Id);
}