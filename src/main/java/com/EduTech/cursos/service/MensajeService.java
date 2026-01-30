package com.EduTech.cursos.service;

import com.EduTech.cursos.client.UsuarioClient;
import com.EduTech.cursos.model.Mensaje;
import com.EduTech.cursos.repository.MensajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MensajeService {

    @Autowired
    private MensajeRepository mensajeRepository;

    @Autowired
    private UsuarioClient usuarioClient;

    public Mensaje enviarMensaje(String contenido, Long remitenteId, Long destinatarioId) {
        usuarioClient.obtenerUsuario(remitenteId);
        usuarioClient.obtenerUsuario(destinatarioId);

        Mensaje mensaje = new Mensaje();
        mensaje.setContenido(contenido);
        mensaje.setRemitenteId(remitenteId);
        mensaje.setDestinatarioId(destinatarioId);
        mensaje.setFechaEnvio(java.time.LocalDateTime.now());

        return mensajeRepository.save(mensaje);
    }


    public List<Mensaje> obtenerChat(Long usuario1Id, Long usuario2Id) {
        return mensajeRepository.findAll();
    }
}