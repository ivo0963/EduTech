package com.EduTech.cursos.controller;

import com.EduTech.cursos.model.Curso;
import com.EduTech.cursos.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @PostMapping
    public ResponseEntity<Curso> crearCurso(@RequestBody Curso curso) {
        return ResponseEntity.ok(cursoService.guardarCurso(curso));
    }

    @GetMapping
    public ResponseEntity<List<Curso>> listarCursos() {
        return ResponseEntity.ok(cursoService.listarCursos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> obtenerCurso(@PathVariable Long id) {
        Optional<Curso> curso = cursoService.obtenerPorId(id);
        return curso.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/instructor/{idInstructor}")
    public ResponseEntity<List<Curso>> listarPorInstructor(@PathVariable Long idInstructor) {
        return ResponseEntity.ok(cursoService.listarCursosPorInstructor(idInstructor));
    }

    @PutMapping("/{id}/aprobar")
    public ResponseEntity<Curso> aprobarCurso(@PathVariable Long id) {
        return ResponseEntity.ok(cursoService.aprobarCurso(id));
    }

    @PutMapping("/{idCurso}/asignar/{idInstructor}")
    public ResponseEntity<Curso> asignarInstructor(@PathVariable Long idCurso, @PathVariable Long idInstructor) {
        return ResponseEntity.ok(cursoService.asignarInstructor(idCurso, idInstructor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCurso(@PathVariable Long id) {
        cursoService.eliminarCurso(id);
        return ResponseEntity.noContent().build();
    }
}