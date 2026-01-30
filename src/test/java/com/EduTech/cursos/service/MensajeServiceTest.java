package com.EduTech.cursos.service;

import com.EduTech.cursos.model.Mensaje;
import com.EduTech.cursos.model.Usuario;
import com.EduTech.cursos.repository.MensajeRepository;
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
class MensajeServiceTest {

    @Mock
    private MensajeRepository mensajeRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private MensajeService mensajeService;

    @Test
    void enviarMensaje() {
        Usuario remitente = new Usuario();
        remitente.setId(1L);
        Usuario destinatario = new Usuario();
        destinatario.setId(2L);
        Mensaje mensaje = new Mensaje();
        mensaje.setContenido("Hola");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(remitente));
        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(destinatario));
        when(mensajeRepository.save(any(Mensaje.class))).thenReturn(mensaje);

        Mensaje resultado = mensajeService.enviarMensaje(1L, 2L, "Hola");

        assertNotNull(resultado);
        assertEquals("Hola", resultado.getContenido());
        verify(mensajeRepository).save(any(Mensaje.class));
    }
}