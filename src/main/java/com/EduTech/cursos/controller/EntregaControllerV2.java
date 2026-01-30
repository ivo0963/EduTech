package com.EduTech.cursos.controller;

import com.EduTech.cursos.assembler.EntregaModelAssembler;
import com.EduTech.cursos.model.Entrega;
import com.EduTech.cursos.repository.EntregaRepository;
import com.EduTech.cursos.repository.EvaluacionRepository;
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
@RequestMapping("/api/v2/entregas")
public class EntregaControllerV2 {

    @Autowired
    private EntregaRepository entregaRepository;

    @Autowired
    private EvaluacionRepository evaluacionRepository;

    @Autowired
    private EntregaModelAssembler entregaModelAssembler;

    @GetMapping
    public CollectionModel<EntityModel<Entrega>> obtenerTodas() {
        List<EntityModel<Entrega>> entregas = entregaRepository.findAll().stream()
                .map(entregaModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(entregas,
                linkTo(methodOn(EntregaControllerV2.class).obtenerTodas()).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Entrega>> obtenerPorId(@PathVariable Long id) {
        Entrega entrega = entregaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrega no encontrada"));
        return ResponseEntity.ok(entregaModelAssembler.toModel(entrega));
    }

    @PostMapping
    public ResponseEntity<EntityModel<Entrega>> entregar(@RequestBody Map<String, Object> payload) {
        Long idEvaluacion = Long.valueOf(payload.get("idEvaluacion").toString());
        Long idEstudiante = Long.valueOf(payload.get("idEstudiante").toString());
        String respuesta = (String) payload.get("respuesta");

        Entrega entrega = new Entrega();
        entrega.setEstudianteId(idEstudiante);
        entrega.setRespuestaAlumno(respuesta);
        entrega.setEvaluacion(evaluacionRepository.findById(idEvaluacion).orElseThrow());

        Entrega guardada = entregaRepository.save(entrega);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(entregaModelAssembler.toModel(guardada));
    }

    @PutMapping("/{id}/calificar")
    public ResponseEntity<EntityModel<Entrega>> calificarEntrega(
            @PathVariable Long id,
            @RequestBody Map<String, Object> payload) {

        Entrega entrega = entregaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrega no encontrada"));

        if (payload.containsKey("calificacion")) {
            Double nota = Double.valueOf(payload.get("calificacion").toString());
            entrega.setCalificacion(nota);
        }

        if (payload.containsKey("feedback")) {
            String feedback = (String) payload.get("feedback");
            entrega.setFeedbackProfesor(feedback);
        }

        Entrega entregaActualizada = entregaRepository.save(entrega);

        return ResponseEntity.ok(entregaModelAssembler.toModel(entregaActualizada));
    }

    @GetMapping("/estudiante/{idEstudiante}")
    public CollectionModel<EntityModel<Entrega>> obtenerPorEstudiante(@PathVariable Long idEstudiante) {
        List<EntityModel<Entrega>> entregas = entregaRepository.findByEstudianteId(idEstudiante).stream()
                .map(entregaModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(entregas,
                linkTo(methodOn(EntregaControllerV2.class).obtenerPorEstudiante(idEstudiante)).withSelfRel());
    }
}