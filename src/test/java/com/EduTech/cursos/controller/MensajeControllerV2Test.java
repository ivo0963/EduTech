package com.EduTech.cursos.controller;

import com.EduTech.cursos.assembler.MensajeModelAssembler;
import com.EduTech.cursos.model.Mensaje;
import com.EduTech.cursos.service.MensajeService;
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

@WebMvcTest(MensajeControllerV2.class)
public class MensajeControllerV2Test {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MensajeService mensajeService;

    @MockitoBean
    private MensajeModelAssembler mensajeAssembler;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void enviarMensaje() throws Exception {
        Mensaje mensaje = new Mensaje();
        mensaje.setId(1L);
        mensaje.setContenido("Hola");

        when(mensajeService.enviarMensaje(anyLong(), anyLong(), any(String.class))).thenReturn(mensaje);
        when(mensajeAssembler.toModel(any(Mensaje.class))).thenReturn(EntityModel.of(mensaje));

        Map<String, Object> payload = new HashMap<>();
        payload.put("idRemitente", 1);
        payload.put("idDestinatario", 2);
        payload.put("contenido", "Hola");

        mockMvc.perform(post("/api/v2/mensajes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isCreated());
    }
}