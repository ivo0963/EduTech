package com.EduTech.cursos.controller;

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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ResenaControllerV2.class)
public class ResenaControllerV2Test {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ResenaService resenaService;

    @MockitoBean
    private ResenaModelAssembler resenaAssembler;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void crearResena() throws Exception {
        Resena resena = new Resena();
        resena.setId(1L);
        resena.setComentario("Muy buen curso");

        when(resenaService.agregarResena(anyLong(), anyLong(), any(Resena.class))).thenReturn(resena);
        when(resenaAssembler.toModel(any(Resena.class))).thenReturn(EntityModel.of(resena));

        Map<String, Object> payload = new HashMap<>();
        payload.put("idUsuario", 1);
        payload.put("idCurso", 1);
        payload.put("comentario", "Muy buen curso");
        payload.put("calificacion", 5);

        mockMvc.perform(post("/api/v2/resenas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isCreated());
    }
}