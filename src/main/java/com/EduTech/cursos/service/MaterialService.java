package com.EduTech.cursos.service;

import com.EduTech.cursos.model.Curso;
import com.EduTech.cursos.model.Material;
import com.EduTech.cursos.repository.CursoRepository;
import com.EduTech.cursos.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private CursoRepository cursoRepository;

    public Material agregarMaterial(Long idCurso, Material material) {
        Curso curso = cursoRepository.findById(idCurso)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con ID: " + idCurso));

        material.setCurso(curso);
        return materialRepository.save(material);
    }

    public List<Material> listarPorCurso(Long idCurso) {
        return materialRepository.findByCursoId(idCurso);
    }

    public Material actualizarMaterial(Long id, Material detalles) {
        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Material no encontrado"));

        material.setTitulo(detalles.getTitulo());
        material.setDescripcion(detalles.getDescripcion());
        material.setUrlRecurso(detalles.getUrlRecurso());

        return materialRepository.save(material);
    }
    public Material obtenerPorId(Long id) {
        return materialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Material no encontrado con ID: " + id));
    }

    public void eliminarMaterial(Long id) {
        materialRepository.deleteById(id);
    }
}