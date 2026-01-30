package com.EduTech.cursos.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "mensajes")
@NoArgsConstructor
@AllArgsConstructor
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String contenido;

    private LocalDateTime fechaEnvio = LocalDateTime.now();

    @Column(name = "id_remitente", nullable = false)
    private Long remitenteId;

    @Column(name = "id_destinatario", nullable = false)
    private Long destinatarioId;
}