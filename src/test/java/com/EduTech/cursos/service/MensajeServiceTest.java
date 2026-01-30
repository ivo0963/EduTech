package com.EduTech.cursos.service;

import com.EduTech.cursos.client.UsuarioClient;
import com.EduTech.cursos.dto.UsuarioDTO;
import com.EduTech.cursos.model.Mensaje;
import com.EduTech.cursos.repository.MensajeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MensajeServiceTest {

    @Mock
    private MensajeRepository mensajeRepository;

    @Mock
    private UsuarioClient usuarioClient;

    @InjectMocks
    private MensajeService mensajeService;

    @Test
    void enviarMensaje() {
        Long remitenteId = 1L;
        Long destinatarioId = 2L;
        String contenido = "Hola compañero";

        Mensaje mensajeGuardado = new Mensaje();
        mensajeGuardado.setContenido(contenido);
        mensajeGuardado.setRemitenteId(remitenteId);

        when(usuarioClient.obtenerUsuario(remitenteId)).thenReturn(new UsuarioDTO());
        when(usuarioClient.obtenerUsuario(destinatarioId)).thenReturn(new UsuarioDTO());

        when(mensajeRepository.save(any(Mensaje.class))).thenReturn(mensajeGuardado);

        Mensaje resultado = mensajeService.enviarMensaje(contenido, remitenteId, destinatarioId);

        assertNotNull(resultado);
        assertEquals(contenido, resultado.getContenido());

        verify(usuarioClient).obtenerUsuario(remitenteId);
        verify(usuarioClient).obtenerUsuario(destinatarioId);
    }
}