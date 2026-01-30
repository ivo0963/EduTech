package com.EduTech.cursos.controller;

import com.EduTech.cursos.assembler.MensajeModelAssembler;
import com.EduTech.cursos.model.Mensaje;
import com.EduTech.cursos.service.MensajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v2/mensajes")
public class MensajeControllerV2 {

    @Autowired
    private MensajeService mensajeService;

    @Autowired
    private MensajeModelAssembler mensajeAssembler;

    @PostMapping
    public ResponseEntity<EntityModel<Mensaje>> enviar(@RequestBody Map<String, Object> payload) {

        Long idRemitente = Long.valueOf(payload.get("idRemitente").toString());
        Long idDestinatario = Long.valueOf(payload.get("idDestinatario").toString());
        String contenido = (String) payload.get("contenido");

        Mensaje mensajeEnviado = mensajeService.enviarMensaje(contenido, idRemitente, idDestinatario);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mensajeAssembler.toModel(mensajeEnviado));
    }

    @GetMapping("/chat")
    public CollectionModel<EntityModel<Mensaje>> verChat(
            @RequestParam Long usuario1,
            @RequestParam Long usuario2) {

        List<EntityModel<Mensaje>> chat = mensajeService.obtenerChat(usuario1, usuario2)
                .stream()
                .map(mensajeAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(chat,
                linkTo(methodOn(MensajeControllerV2.class).verChat(usuario1, usuario2)).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Mensaje>> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.notFound().build();
    }
}