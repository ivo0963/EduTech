package com.EduTech.cursos.controller;

import com.EduTech.cursos.assembler.CursoModelAssembler;
import com.EduTech.cursos.model.Curso;
import com.EduTech.cursos.service.CursoService;
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

@WebMvcTest(CursoControllerV2.class)
public class CursoControllerV2Test {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CursoService cursoService;

    @MockitoBean
    private CursoModelAssembler cursoAssembler;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void crearCurso() throws Exception {
        Curso curso = new Curso();
        curso.setId(1L);
        curso.setTitulo("Java Spring");
        curso.setInstructorId(1L);

        when(cursoService.guardarCurso(any(Curso.class))).thenReturn(curso);


        when(cursoAssembler.toModel(any(Curso.class))).thenReturn(EntityModel.of(curso));

        Map<String, Object> payload = new HashMap<>();
        payload.put("titulo", "Java Spring");
        payload.put("descripcion", "Curso Avanzado");
        payload.put("instructorId", 1);

        mockMvc.perform(post("/api/v2/cursos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isCreated());
    }
}