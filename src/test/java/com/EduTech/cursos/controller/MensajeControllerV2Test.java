package com.EduTech.cursos.controller;

import com.EduTech.cursos.model.Mensaje;
import com.EduTech.cursos.service.MensajeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MensajeController.class)
public class MensajeControllerV2Test {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MensajeService mensajeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void enviarMensaje() throws Exception {
        Mensaje mensaje = new Mensaje();
        mensaje.setId(1L);
        mensaje.setContenido("Hola Mundo");
        mensaje.setRemitenteId(10L);
        mensaje.setDestinatarioId(20L);
        mensaje.setFechaEnvio(LocalDateTime.now());

        when(mensajeService.enviarMensaje(anyString(), anyLong(), anyLong())).thenReturn(mensaje);

        Map<String, Object> payload = new HashMap<>();
        payload.put("contenido", "Hola Mundo");
        payload.put("remitenteId", 10);
        payload.put("destinatarioId", 20);

        mockMvc.perform(post("/mensajes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isOk());
    }
}