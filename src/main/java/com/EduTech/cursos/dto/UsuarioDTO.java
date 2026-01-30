package com.EduTech.cursos.dto;

import lombok.Data;

@Data
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String email;
    private String rol;
}