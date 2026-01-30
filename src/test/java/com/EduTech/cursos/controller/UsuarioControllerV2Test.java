package com.EduTech.cursos.controller;

import com.EduTech.cursos.model.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsuarioControllerV2.class)
public class UsuarioControllerV2Test {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UsuarioService usuarioService;

    @MockitoBean
    private UsuarioModelAssembler usuarioAssembler;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registrarUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Test User");
        usuario.setEmail("test@email.com");

        when(usuarioService.registrar(any(Usuario.class))).thenReturn(usuario);
        when(usuarioAssembler.toModel(any(Usuario.class))).thenReturn(EntityModel.of(usuario));

        Map<String, Object> payload = new HashMap<>();
        payload.put("nombre", "Test User");
        payload.put("email", "test@email.com");
        payload.put("password", "123456");
        payload.put("rol", "ESTUDIANTE");

        mockMvc.perform(post("/api/v2/usuarios/registro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isCreated());
    }
}