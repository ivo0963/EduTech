package com.EduTech.cursos.controller;

import com.EduTech.cursos.model.Mensaje;
import com.EduTech.cursos.service.MensajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mensajes")
public class MensajeController {

    @Autowired
    private MensajeService mensajeService;

    @PostMapping
    public Mensaje enviar(@RequestBody Map<String, Object> payload) {
        Long idRemitente = Long.valueOf(payload.get("idRemitente").toString());
        Long idDestinatario = Long.valueOf(payload.get("idDestinatario").toString());
        String contenido = (String) payload.get("contenido");

        return mensajeService.enviarMensaje(contenido, idRemitente, idDestinatario);
    }

    @GetMapping("/chat")
    public List<Mensaje> verChat(@RequestParam Long usuario1, @RequestParam Long usuario2) {
        return mensajeService.obtenerChat(usuario1, usuario2);
    }
}