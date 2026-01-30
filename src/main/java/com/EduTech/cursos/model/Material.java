package com.EduTech.cursos.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "materiales")
@NoArgsConstructor
@AllArgsConstructor
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    private String descripcion;

    @Column(nullable = false)
    private String urlRecurso;

    @ManyToOne
    @JoinColumn(name = "id_curso", nullable = false)
    private Curso curso;
}