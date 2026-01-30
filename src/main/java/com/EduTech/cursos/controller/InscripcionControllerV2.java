package com.EduTech.cursos.controller;

import com.EduTech.cursos.assembler.InscripcionModelAssembler;
import com.EduTech.cursos.model.Inscripcion;
import com.EduTech.cursos.service.InscripcionService;
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
@RequestMapping("/api/v2/inscripciones")
public class InscripcionControllerV2 {

    @Autowired
    private InscripcionService inscripcionService;

    @Autowired
    private InscripcionModelAssembler inscripcionAssembler;

    @PostMapping
    public ResponseEntity<EntityModel<Inscripcion>> inscribir(@RequestBody Map<String, Object> payload) {
        Long idUsuario = Long.valueOf(payload.get("idUsuario").toString());
        Long idCurso = Long.valueOf(payload.get("idCurso").toString());

        Inscripcion nuevaInscripcion = inscripcionService.inscribirUsuario(idUsuario, idCurso);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(inscripcionAssembler.toModel(nuevaInscripcion));
    }

    @PutMapping("/{id}/progreso")
    public ResponseEntity<EntityModel<Inscripcion>> actualizarProgreso(
            @PathVariable Long id,
            @RequestBody Map<String, Object> payload) {

        Double porcentaje = Double.valueOf(payload.get("porcentaje").toString());
        Inscripcion actualizado = inscripcionService.actualizarProgreso(id, porcentaje);

        return ResponseEntity.ok(inscripcionAssembler.toModel(actualizado));
    }

    @GetMapping("/usuario/{idUsuario}")
    public CollectionModel<EntityModel<Inscripcion>> listarPorUsuario(@PathVariable Long idUsuario) {
        List<EntityModel<Inscripcion>> inscripciones = inscripcionService.obtenerInscripcionesPorUsuario(idUsuario)
                .stream()
                .map(inscripcionAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(inscripciones,
                linkTo(methodOn(InscripcionControllerV2.class).listarPorUsuario(idUsuario)).withSelfRel());
    }

    @GetMapping("/curso/{idCurso}")
    public CollectionModel<EntityModel<Inscripcion>> listarPorCurso(@PathVariable Long idCurso) {
        List<EntityModel<Inscripcion>> inscripciones = inscripcionService.obtenerInscripcionesPorCurso(idCurso)
                .stream()
                .map(inscripcionAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(inscripciones,
                linkTo(methodOn(InscripcionControllerV2.class).listarPorCurso(idCurso)).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Inscripcion>> obtenerPorId(@PathVariable Long id) {
        Inscripcion inscripcion = inscripcionService.obtenerPorId(id);
        return ResponseEntity.ok(inscripcionAssembler.toModel(inscripcion));
    }
}