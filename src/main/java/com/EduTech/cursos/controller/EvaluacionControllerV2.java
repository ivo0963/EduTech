package com.EduTech.cursos.controller;

import com.EduTech.cursos.assembler.EvaluacionModelAssembler;
import com.EduTech.cursos.model.Entrega;
import com.EduTech.cursos.model.Evaluacion;
import com.EduTech.cursos.service.EvaluacionService;
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
@RequestMapping("/api/v2/evaluaciones")
public class EvaluacionControllerV2 {

    @Autowired
    private EvaluacionService evaluacionService;

    @Autowired
    private EvaluacionModelAssembler evaluacionAssembler;

    @PostMapping
    public ResponseEntity<EntityModel<Evaluacion>> crear(@RequestBody Map<String, Object> payload) {

        String titulo = (String) payload.get("titulo");
        String descripcion = (String) payload.get("descripcion");
        Long idCurso = Long.valueOf(payload.get("idCurso").toString());
        Evaluacion nuevaEvaluacion = new Evaluacion();
        nuevaEvaluacion.setTitulo(titulo);
        nuevaEvaluacion.setDescripcion(descripcion);
        Evaluacion creada = evaluacionService.crearEvaluacion(idCurso, nuevaEvaluacion);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(evaluacionAssembler.toModel(creada));
    }

    @GetMapping("/{id}")
    public EntityModel<Evaluacion> obtenerPorId(@PathVariable Long id) {
        throw new UnsupportedOperationException("Falta agregar obtenerPorId en EvaluacionService");
    }

    @GetMapping("/curso/{idCurso}")
    public CollectionModel<EntityModel<Evaluacion>> listarPorCurso(@PathVariable Long idCurso) {
        List<EntityModel<Evaluacion>> evaluaciones = evaluacionService.listarPorCurso(idCurso).stream()
                .map(evaluacionAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(evaluaciones,
                linkTo(methodOn(EvaluacionControllerV2.class).listarPorCurso(idCurso)).withSelfRel());
    }


    @PostMapping("/entregar")
    public ResponseEntity<Entrega> entregar(@RequestBody Map<String, Object> payload) {
        Long idEvaluacion = Long.valueOf(payload.get("idEvaluacion").toString());
        Long idEstudiante = Long.valueOf(payload.get("idEstudiante").toString());
        String respuesta = (String) payload.get("respuesta");

        Entrega entrega = evaluacionService.enviarRespuesta(idEvaluacion, idEstudiante, respuesta);

        return ResponseEntity.ok(entrega);
    }

    @PutMapping("/calificar/{idEntrega}")
    public ResponseEntity<Entrega> calificar(@PathVariable Long idEntrega, @RequestBody Map<String, Object> payload) {
        Double nota = Double.valueOf(payload.get("nota").toString());
        String feedback = (String) payload.get("feedback");

        Entrega entregaCalificada = evaluacionService.calificarEntrega(idEntrega, nota, feedback);

        return ResponseEntity.ok(entregaCalificada);
    }

    @GetMapping("/{idEvaluacion}/entregas")
    public ResponseEntity<List<Entrega>> verEntregas(@PathVariable Long idEvaluacion) {
        List<Entrega> entregas = evaluacionService.listarEntregasPorEvaluacion(idEvaluacion);
        return ResponseEntity.ok(entregas);
    }
}