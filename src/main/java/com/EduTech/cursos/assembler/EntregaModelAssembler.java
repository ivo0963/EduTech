package com.EduTech.cursos.assembler;

import com.EduTech.cursos.controller.EntregaControllerV2;
import com.EduTech.cursos.model.Entrega;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class EntregaModelAssembler implements RepresentationModelAssembler<Entrega, EntityModel<Entrega>> {

    @Override
    public EntityModel<Entrega> toModel(Entrega entrega) {

        String estadoCalificacion = (entrega.getCalificacion() == null)
                ? "En revisión"
                : String.valueOf(entrega.getCalificacion());

        EntityModel<Entrega> model = EntityModel.of(entrega);

        model.add(linkTo(methodOn(EntregaControllerV2.class).obtenerPorId(entrega.getId())).withSelfRel());

        model.add(linkTo(methodOn(EntregaControllerV2.class)
                .obtenerPorEstudiante(entrega.getEstudianteId()))
                .withRel("entregas-estudiante"));

        return model;
    }
}