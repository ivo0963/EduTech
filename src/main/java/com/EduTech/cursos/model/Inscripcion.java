package com.EduTech.cursos.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "inscripciones")
@NoArgsConstructor
@AllArgsConstructor
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "id_usuario", nullable = false)
    private Long estudianteId;

    @ManyToOne
    @JoinColumn(name = "id_curso", nullable = false)
    private Curso curso;

    private LocalDate fechaInscripcion;

    private double progreso;
}