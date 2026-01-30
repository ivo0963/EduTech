package com.EduTech.cursos.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "cursos")
@NoArgsConstructor
@AllArgsConstructor
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private String estado;

    @Column(name = "id_instructor", nullable = false)
    private Long instructorId;



}