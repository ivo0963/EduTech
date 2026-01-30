package com.EduTech.cursos.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "usuario")
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String rol;
}