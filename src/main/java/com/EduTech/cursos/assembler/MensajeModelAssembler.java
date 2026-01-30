package com.EduTech.cursos.assembler;

import com.EduTech.cursos.controller.MensajeControllerV2;
import com.EduTech.cursos.model.Mensaje;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class MensajeModelAssembler implements RepresentationModelAssembler<Mensaje, EntityModel<Mensaje>> {

    @Override
    public EntityModel<Mensaje> toModel(Mensaje mensaje) {

        EntityModel<Mensaje> model = EntityModel.of(mensaje);

        model.add(linkTo(methodOn(MensajeControllerV2.class)
                .obtenerPorId(mensaje.getId())).withSelfRel());

        model.add(linkTo(methodOn(MensajeControllerV2.class)
                .verChat(mensaje.getRemitenteId(), mensaje.getDestinatarioId()))
                .withRel("ver-conversacion-completa"));

        return model;
    }
}