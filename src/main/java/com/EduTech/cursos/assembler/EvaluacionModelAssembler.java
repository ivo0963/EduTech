package com.EduTech.cursos.assembler;

import com.EduTech.cursos.controller.EvaluacionControllerV2;
import com.EduTech.cursos.model.Evaluacion;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class EvaluacionModelAssembler implements RepresentationModelAssembler<Evaluacion, EntityModel<Evaluacion>> {

    @Override
    public EntityModel<Evaluacion> toModel(Evaluacion evaluacion) {

        EntityModel<Evaluacion> model = EntityModel.of(evaluacion);

        model.add(linkTo(methodOn(EvaluacionControllerV2.class)
                .obtenerPorId(evaluacion.getId())).withSelfRel());

        model.add(linkTo(methodOn(EvaluacionControllerV2.class)
                .verEntregas(evaluacion.getId())).withRel("ver-entregas"));

        if (evaluacion.getCurso() != null) {
            model.add(linkTo(methodOn(EvaluacionControllerV2.class)
                    .listarPorCurso(evaluacion.getCurso().getId())).withRel("volver-al-curso"));
        }

        return model;
    }
}