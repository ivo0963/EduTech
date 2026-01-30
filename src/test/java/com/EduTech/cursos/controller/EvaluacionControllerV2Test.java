package com.EduTech.cursos.controller;

import com.EduTech.cursos.assembler.EvaluacionModelAssembler;
import com.EduTech.cursos.model.Evaluacion;
import com.EduTech.cursos.service.EvaluacionService;
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

@WebMvcTest(EvaluacionControllerV2.class)
public class EvaluacionControllerV2Test {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EvaluacionService evaluacionService;

    @MockitoBean
    private EvaluacionModelAssembler evaluacionAssembler;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void crearEvaluacion() throws Exception {
        Evaluacion evaluacion = new Evaluacion();
        evaluacion.setId(1L);
        evaluacion.setTitulo("Examen Final");

        when(evaluacionService.crearEvaluacion(anyLong(), any(Evaluacion.class))).thenReturn(evaluacion);
        when(evaluacionAssembler.toModel(any(Evaluacion.class))).thenReturn(EntityModel.of(evaluacion));

        Map<String, Object> payload = new HashMap<>();
        payload.put("idCurso", 1);
        payload.put("titulo", "Examen Final");
        payload.put("descripcion", "Difícil");

        mockMvc.perform(post("/api/v2/evaluaciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isCreated());
    }
}