package com.EduTech.cursos.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "entregas")
@Data
public class Entrega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String respuestaAlumno;

    private Double calificacion;

    private String feedbackProfesor;

    private LocalDateTime fechaEntrega;

    @Column(name = "id_estudiante", nullable = false)
    private Long estudianteId;

    @ManyToOne
    @JoinColumn(name = "id_evaluacion", nullable = false)
    private Evaluacion evaluacion;

    @PrePersist
    public void prePersist() {
        this.fechaEntrega = LocalDateTime.now();
    }
}