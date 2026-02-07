package com.sweetlab.service;

import com.sweetlab.entity.ContactoEntity;
import com.sweetlab.repository.ContactoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ContactoService {
    
    @Autowired
    private ContactoRepository contactoRepository;

    public ContactoEntity guardarContacto(ContactoEntity contacto) {
        if (contacto.getFechaContacto() == null) {
            contacto.setFechaContacto(LocalDateTime.now());
        }
        return contactoRepository.save(contacto);
    }

    public List<ContactoEntity> obtenerTodos() {
        return contactoRepository.findAll();
    }

    public Optional<ContactoEntity> obtenerPorId(Long id) {
        return contactoRepository.findById(id);
    }

    public List<ContactoEntity> obtenerPorAsunto(String asunto) {
        return contactoRepository.findByAsunto(asunto);
    }

    public List<ContactoEntity> obtenerPorEmail(String email) {
        return contactoRepository.findByEmail(email);
    }

    public List<ContactoEntity> obtenerPorFecha(LocalDateTime inicio, LocalDateTime fin) {
        return contactoRepository.findByFechaContactoBetween(inicio, fin);
    }

    public ContactoEntity actualizar(Long id, ContactoEntity contactoActualizado) {
        Optional<ContactoEntity> contactoExistente = contactoRepository.findById(id);
        if (contactoExistente.isPresent()) {
            ContactoEntity contacto = contactoExistente.get();
            contacto.setNombre(contactoActualizado.getNombre());
            contacto.setEmail(contactoActualizado.getEmail());
            contacto.setTelefono(contactoActualizado.getTelefono());
            contacto.setAsunto(contactoActualizado.getAsunto());
            contacto.setMensaje(contactoActualizado.getMensaje());
            return contactoRepository.save(contacto);
        }
        return null;
    }

    public boolean eliminar(Long id) {
        if (contactoRepository.existsById(id)) {
            contactoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
