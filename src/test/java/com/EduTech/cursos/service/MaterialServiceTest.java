package com.EduTech.cursos.service;

import com.EduTech.cursos.model.Curso;
import com.EduTech.cursos.model.Material;
import com.EduTech.cursos.repository.CursoRepository;
import com.EduTech.cursos.repository.MaterialRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MaterialServiceTest {

    @Mock
    private MaterialRepository materialRepository;

    @Mock
    private CursoRepository cursoRepository;

    @InjectMocks
    private MaterialService materialService;

    @Test
    void agregarMaterial() {
        Curso curso = new Curso();
        curso.setId(1L);
        Material material = new Material();
        material.setTitulo("PDF");

        when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));
        when(materialRepository.save(any(Material.class))).thenReturn(material);

        Material resultado = materialService.agregarMaterial(1L, material);

        assertNotNull(resultado);
        assertEquals("PDF", resultado.getTitulo());
        verify(materialRepository).save(any(Material.class));
    }
}