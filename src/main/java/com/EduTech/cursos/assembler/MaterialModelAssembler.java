package com.EduTech.cursos.assembler;

import com.EduTech.cursos.controller.MaterialControllerV2;
import com.EduTech.cursos.model.Material;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class MaterialModelAssembler implements RepresentationModelAssembler<Material, EntityModel<Material>> {

    @Override
    public EntityModel<Material> toModel(Material material) {

        EntityModel<Material> model = EntityModel.of(material);

        model.add(linkTo(methodOn(MaterialControllerV2.class)
                .obtenerPorId(material.getId())).withSelfRel());

        if (material.getCurso() != null) {
            model.add(linkTo(methodOn(MaterialControllerV2.class)
                    .listarPorCurso(material.getCurso().getId())).withRel("materiales-del-curso"));
        }

        model.add(linkTo(methodOn(MaterialControllerV2.class)
                .eliminarMaterial(material.getId())).withRel("eliminar"));

        return model;
    }
}