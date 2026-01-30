package com.EduTech.cursos.controller;

import com.EduTech.cursos.model.Inscripcion;
import com.EduTech.cursos.service.InscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inscripciones")
public class InscripcionController {

    @Autowired
    private InscripcionService inscripcionService;

    @PostMapping
    public Inscripcion inscribir(@RequestParam Long idUsuario, @RequestParam Long idCurso) {
        // CORREGIDO: inscribirUsuario
        return inscripcionService.inscribirUsuario(idUsuario, idCurso);
    }

    @GetMapping("/usuario/{idUsuario}")
    public List<Inscripcion> listarPorUsuario(@PathVariable Long idUsuario) {
        return inscripcionService.obtenerInscripcionesPorUsuario(idUsuario);
    }

    @GetMapping("/curso/{idCurso}")
    public List<Inscripcion> listarPorCurso(@PathVariable Long idCurso) {
        return inscripcionService.obtenerInscripcionesPorCurso(idCurso);
    }

    @PutMapping("/{idInscripcion}/progreso")
    public Inscripcion actualizarProgreso(@PathVariable Long idInscripcion, @RequestParam Double porcentaje) {
        return inscripcionService.actualizarProgreso(idInscripcion, porcentaje);
    }
}