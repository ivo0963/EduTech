package com.EduTech.cursos.controller;

import com.EduTech.cursos.model.Entrega;
import com.EduTech.cursos.model.Evaluacion;
import com.EduTech.cursos.service.EvaluacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/evaluaciones")
public class EvaluacionController {

    @Autowired
    private EvaluacionService evaluacionService;

    // POST http://localhost:8081/evaluaciones?idCurso=1
    @PostMapping
    public Evaluacion crear(@RequestParam Long idCurso, @RequestBody Evaluacion evaluacion) {
        return evaluacionService.crearEvaluacion(idCurso, evaluacion);
    }

    // POST http://localhost:8081/evaluaciones/entregar
    @PostMapping("/entregar")
    public Entrega entregar(@RequestBody Map<String, Object> payload) {
        Long idEvaluacion = Long.valueOf(payload.get("idEvaluacion").toString());
        Long idEstudiante = Long.valueOf(payload.get("idEstudiante").toString());
        String respuesta = (String) payload.get("respuesta");

        return evaluacionService.enviarRespuesta(idEvaluacion, idEstudiante, respuesta);
    }

    // PUT http://localhost:8081/evaluaciones/calificar/1
    @PutMapping("/calificar/{idEntrega}")
    public Entrega calificar(@PathVariable Long idEntrega, @RequestBody Map<String, Object> payload) {
        Double nota = Double.valueOf(payload.get("nota").toString());
        String feedback = (String) payload.get("feedback");

        return evaluacionService.calificarEntrega(idEntrega, nota, feedback);
    }

    @GetMapping("/curso/{idCurso}")
    public List<Evaluacion> listarPorCurso(@PathVariable Long idCurso) {
        return evaluacionService.listarPorCurso(idCurso);
    }

    @GetMapping("/{idEvaluacion}/entregas")
    public List<Entrega> verEntregas(@PathVariable Long idEvaluacion) {
        return evaluacionService.listarEntregasPorEvaluacion(idEvaluacion);
    }
}