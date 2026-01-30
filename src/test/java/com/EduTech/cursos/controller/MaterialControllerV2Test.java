package com.EduTech.cursos.controller;

import com.EduTech.cursos.assembler.MaterialModelAssembler;
import com.EduTech.cursos.model.Material;
import com.EduTech.cursos.service.MaterialService;
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

@WebMvcTest(MaterialControllerV2.class)
public class MaterialControllerV2Test {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MaterialService materialService;

    @MockitoBean
    private MaterialModelAssembler materialAssembler;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void agregarMaterial() throws Exception {
        Material material = new Material();
        material.setId(1L);
        material.setTitulo("PDF Clase 1");

        when(materialService.agregarMaterial(anyLong(), any(Material.class))).thenReturn(material);
        when(materialAssembler.toModel(any(Material.class))).thenReturn(EntityModel.of(material));

        Map<String, Object> payload = new HashMap<>();
        payload.put("idCurso", 1);
        payload.put("titulo", "PDF Clase 1");
        payload.put("descripcion", "Introducción");
        payload.put("urlRecurso", "http://google.com");

        mockMvc.perform(post("/api/v2/materiales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isCreated());
    }
}