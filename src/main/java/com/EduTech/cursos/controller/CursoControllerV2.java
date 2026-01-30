package com.EduTech.cursos.controller;

import com.EduTech.cursos.assembler.CursoModelAssembler;
import com.EduTech.cursos.model.Curso;
import com.EduTech.cursos.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v2/cursos")
public class CursoControllerV2 {

    @Autowired
    private CursoService cursoService;

    @Autowired
    private CursoModelAssembler cursoAssembler;

    @GetMapping
    public CollectionModel<EntityModel<Curso>> listarCursos() {
        List<EntityModel<Curso>> cursos = cursoService.listarCursos().stream()
                .map(cursoAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(cursos,
                linkTo(methodOn(CursoControllerV2.class).listarCursos()).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Curso>> obtenerCurso(@PathVariable Long id) {
        return cursoService.obtenerPorId(id)
                .map(cursoAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EntityModel<Curso>> crearCurso(@RequestBody Curso curso) {
        Curso nuevoCurso = cursoService.guardarCurso(curso);
        return ResponseEntity
                .created(linkTo(methodOn(CursoControllerV2.class).obtenerCurso(nuevoCurso.getId())).toUri())
                .body(cursoAssembler.toModel(nuevoCurso));
    }

    @PutMapping("/{id}/asignar")
    public ResponseEntity<EntityModel<Curso>> asignarInstructor(
            @PathVariable Long id,
            @RequestBody Map<String, Object> payload) {

        if (payload == null || !payload.containsKey("idInstructor")) {
            throw new RuntimeException("Falta idInstructor en el body");
        }

        Long nuevoIdInstructor = Long.valueOf(payload.get("idInstructor").toString());
        Curso cursoActualizado = cursoService.asignarInstructor(id, nuevoIdInstructor);

        return ResponseEntity.ok(cursoAssembler.toModel(cursoActualizado));
    }

    @PutMapping("/{id}/aprobar")
    public ResponseEntity<EntityModel<Curso>> aprobarCurso(@PathVariable Long id) {
        Curso cursoAprobado = cursoService.aprobarCurso(id);
        return ResponseEntity.ok(cursoAssembler.toModel(cursoAprobado));
    }

    @GetMapping("/instructor/{idInstructor}")
    public CollectionModel<EntityModel<Curso>> listarPorInstructor(@PathVariable Long idInstructor) {
        List<EntityModel<Curso>> cursos = cursoService.listarCursosPorInstructor(idInstructor).stream()
                .map(cursoAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(cursos,
                linkTo(methodOn(CursoControllerV2.class).listarPorInstructor(idInstructor)).withSelfRel());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCurso(@PathVariable Long id) {
        if (cursoService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        cursoService.eliminarCurso(id);
        return ResponseEntity.noContent().build();
    }
}