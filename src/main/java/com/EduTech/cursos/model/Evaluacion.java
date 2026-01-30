package com.EduTech.cursos.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "evaluaciones")
@NoArgsConstructor
@AllArgsConstructor
public class Evaluacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "id_curso", nullable = false)
    private Curso curso;
}