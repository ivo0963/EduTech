package com.EduTech.cursos.client;

import com.EduTech.cursos.dto.UsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-usuarios", url = "http://localhost:8082")
public interface UsuarioClient {

    @GetMapping("/usuarios/{id}")
    UsuarioDTO obtenerUsuario(@PathVariable Long id);
}