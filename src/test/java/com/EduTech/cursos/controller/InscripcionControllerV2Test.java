package com.EduTech.cursos.controller;

import com.EduTech.cursos.model.Curso;
import com.EduTech.cursos.model.Inscripcion;
import com.EduTech.cursos.service.InscripcionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InscripcionController.class)
public class InscripcionControllerV2Test {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private InscripcionService inscripcionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void inscribirAlumno() throws Exception {
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setId(1L);
        inscripcion.setEstudianteId(1L);
        inscripcion.setFechaInscripcion(LocalDate.now());

        Curso curso = new Curso();
        curso.setId(1L);
        inscripcion.setCurso(curso);

        when(inscripcionService.inscribirUsuario(anyLong(), anyLong())).thenReturn(inscripcion);

        Map<String, Object> payload = new HashMap<>();
        payload.put("usuarioId", 1);
        payload.put("cursoId", 1);

        mockMvc.perform(post("/inscripciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isOk());
    }
}