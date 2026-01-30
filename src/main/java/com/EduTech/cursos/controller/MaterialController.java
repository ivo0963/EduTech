package com.EduTech.cursos.controller;

import com.EduTech.cursos.model.Material;
import com.EduTech.cursos.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/materiales")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    // POST http://localhost:8081/materiales?idCurso=1
    @PostMapping
    public Material agregarMaterial(@RequestParam Long idCurso, @RequestBody Material material) {
        return materialService.agregarMaterial(idCurso, material);
    }

    // GET http://localhost:8081/materiales/curso/1
    @GetMapping("/curso/{idCurso}")
    public List<Material> listarMaterialesPorCurso(@PathVariable Long idCurso) {
        return materialService.listarPorCurso(idCurso);
    }

    @PutMapping("/{id}")
    public Material actualizarMaterial(@PathVariable Long id, @RequestBody Material material) {
        return materialService.actualizarMaterial(id, material);
    }

    @DeleteMapping("/{id}")
    public void eliminarMaterial(@PathVariable Long id) {
        materialService.eliminarMaterial(id);
    }
}