package com.EduTech.cursos.controller;

import com.EduTech.cursos.assembler.InscripcionModelAssembler;
import com.EduTech.cursos.model.Inscripcion;
import com.EduTech.cursos.service.InscripcionService;
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

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InscripcionControllerV2.class)
public class InscripcionControllerV2Test {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private InscripcionService inscripcionService;

    @MockitoBean
    private InscripcionModelAssembler inscripcionAssembler;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void inscribirAlumno() throws Exception {
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setId(1L);

        when(inscripcionService.inscribir(anyLong(), anyLong())).thenReturn(inscripcion);
        when(inscripcionAssembler.toModel(any(Inscripcion.class))).thenReturn(EntityModel.of(inscripcion));

        Map<String, Object> payload = new HashMap<>();
        payload.put("idUsuario", 1);
        payload.put("idCurso", 1);

        mockMvc.perform(post("/api/v2/inscripciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isCreated());
    }
}