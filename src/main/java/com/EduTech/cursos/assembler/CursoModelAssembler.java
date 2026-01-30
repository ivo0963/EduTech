package com.EduTech.cursos.assembler;

import com.EduTech.cursos.controller.CursoControllerV2;
import com.EduTech.cursos.model.Curso;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class CursoModelAssembler implements RepresentationModelAssembler<Curso, EntityModel<Curso>> {

    @Override
    public EntityModel<Curso> toModel(Curso curso) {

        EntityModel<Curso> model = EntityModel.of(curso);

        model.add(linkTo(methodOn(CursoControllerV2.class).obtenerCurso(curso.getId())).withSelfRel());

        model.add(linkTo(methodOn(CursoControllerV2.class).listarCursos()).withRel("lista-cursos"));

        if (!"APROBADO".equals(curso.getEstado())) {
            model.add(linkTo(methodOn(CursoControllerV2.class).aprobarCurso(curso.getId())).withRel("aprobar-curso"));
        }

        model.add(linkTo(methodOn(CursoControllerV2.class)
                .asignarInstructor(curso.getId(), null))
                .withRel("asignar-instructor"));

        return model;
    }
}