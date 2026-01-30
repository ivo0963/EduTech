package com.EduTech.cursos.controller;

import com.EduTech.cursos.assembler.MaterialModelAssembler;
import com.EduTech.cursos.model.Material;
import com.EduTech.cursos.service.MaterialService;
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
@RequestMapping("/api/v2/materiales")
public class MaterialControllerV2 {

    @Autowired
    private MaterialService materialService;

    @Autowired
    private MaterialModelAssembler materialAssembler;

    @PostMapping
    public ResponseEntity<EntityModel<Material>> agregar(@RequestBody Map<String, Object> payload) {

        Long idCurso = Long.valueOf(payload.get("idCurso").toString());
        String titulo = (String) payload.get("titulo");
        String descripcion = (String) payload.get("descripcion");
        String urlRecurso = (String) payload.get("urlRecurso");

        Material nuevoMaterial = new Material();
        nuevoMaterial.setTitulo(titulo);
        nuevoMaterial.setDescripcion(descripcion);
        nuevoMaterial.setUrlRecurso(urlRecurso);

        Material guardado = materialService.agregarMaterial(idCurso, nuevoMaterial);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(materialAssembler.toModel(guardado));
    }

    @GetMapping("/curso/{idCurso}")
    public CollectionModel<EntityModel<Material>> listarPorCurso(@PathVariable Long idCurso) {
        List<EntityModel<Material>> materiales = materialService.listarPorCurso(idCurso)
                .stream()
                .map(materialAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(materiales,
                linkTo(methodOn(MaterialControllerV2.class).listarPorCurso(idCurso)).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Material>> obtenerPorId(@PathVariable Long id) {
        Material material = materialService.obtenerPorId(id);
        return ResponseEntity.ok(materialAssembler.toModel(material));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Material>> actualizar(@PathVariable Long id, @RequestBody Map<String, Object> payload) {

        Material detalles = new Material();
        detalles.setTitulo((String) payload.get("titulo"));
        detalles.setDescripcion((String) payload.get("descripcion"));
        detalles.setUrlRecurso((String) payload.get("urlRecurso"));

        Material actualizado = materialService.actualizarMaterial(id, detalles);

        return ResponseEntity.ok(materialAssembler.toModel(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMaterial(@PathVariable Long id) {
        materialService.eliminarMaterial(id);
        return ResponseEntity.noContent().build();
    }
}