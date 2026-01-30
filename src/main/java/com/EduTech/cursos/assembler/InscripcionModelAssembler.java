package com.EduTech.cursos.assembler;

import com.EduTech.cursos.controller.InscripcionControllerV2;
import com.EduTech.cursos.model.Inscripcion;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class InscripcionModelAssembler implements RepresentationModelAssembler<Inscripcion, EntityModel<Inscripcion>> {

    @Override
    public EntityModel<Inscripcion> toModel(Inscripcion inscripcion) {

        EntityModel<Inscripcion> model = EntityModel.of(inscripcion);

        model.add(linkTo(methodOn(InscripcionControllerV2.class)
                .obtenerPorId(inscripcion.getId())).withSelfRel());

        model.add(linkTo(methodOn(InscripcionControllerV2.class)
                .actualizarProgreso(inscripcion.getId(), null)).withRel("actualizar-progreso"));

        model.add(linkTo(methodOn(InscripcionControllerV2.class)
                .listarPorCurso(inscripcion.getCurso().getId())).withRel("ver-curso-completo"));

        return model;
    }
}