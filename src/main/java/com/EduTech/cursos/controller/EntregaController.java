package com.EduTech.cursos.controller;

import com.EduTech.cursos.model.Entrega;
import com.EduTech.cursos.service.EntregaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/entregas")
public class EntregaController {

    @Autowired
    private EntregaService entregaService;

    @PostMapping
    public Entrega entregar(@RequestBody Map<String, Object> payload) {
        Long idEvaluacion = Long.valueOf(payload.get("idEvaluacion").toString());
        Long idEstudiante = Long.valueOf(payload.get("idEstudiante").toString());
        String respuesta = (String) payload.get("respuesta");

        return entregaService.realizarEntrega(idEvaluacion, idEstudiante, respuesta);
    }

    @PutMapping("/calificar/{idEntrega}")
    public Entrega calificar(@PathVariable Long idEntrega, @RequestBody Map<String, Object> payload) {
        Double nota = Double.valueOf(payload.get("nota").toString());
        String feedback = (String) payload.get("feedback");

        return entregaService.calificarEntrega(idEntrega, nota, feedback);
    }

    @GetMapping("/evaluacion/{idEvaluacion}")
    public List<Entrega> listarPorEvaluacion(@PathVariable Long idEvaluacion) {
        return entregaService.listarPorEvaluacion(idEvaluacion);
    }

    @GetMapping("/estudiante/{idEstudiante}")
    public List<Entrega> listarPorEstudiante(@PathVariable Long idEstudiante) {
        return entregaService.listarPorEstudiante(idEstudiante);
    }
}