package com.sweetlab.controller;

import com.sweetlab.entity.ContactoEntity;
import com.sweetlab.service.ContactoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contactos")
@CrossOrigin(origins = "*")
public class ContactoController {
    
    @Autowired
    private ContactoService contactoService;

    @PostMapping
    public ResponseEntity<?> crearContacto(@RequestBody ContactoEntity contacto) {
        try {
            if (contacto.getNombre() == null || contacto.getNombre().isEmpty() ||
                contacto.getEmail() == null || contacto.getEmail().isEmpty() ||
                contacto.getAsunto() == null || contacto.getAsunto().isEmpty() ||
                contacto.getMensaje() == null || contacto.getMensaje().isEmpty()) {
                return ResponseEntity.badRequest().body("Los campos nombre, email, asunto y mensaje son requeridos");
            }

            ContactoEntity nuevoContacto = contactoService.guardarContacto(contacto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoContacto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar contacto: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<ContactoEntity>> obtenerTodos() {
        List<ContactoEntity> contactos = contactoService.obtenerTodos();
        return ResponseEntity.ok(contactos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        Optional<ContactoEntity> contacto = contactoService.obtenerPorId(id);
        if (contacto.isPresent()) {
            return ResponseEntity.ok(contacto.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contacto no encontrado");
    }

    @GetMapping("/asunto/{asunto}")
    public ResponseEntity<List<ContactoEntity>> obtenerPorAsunto(@PathVariable String asunto) {
        List<ContactoEntity> contactos = contactoService.obtenerPorAsunto(asunto);
        return ResponseEntity.ok(contactos);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<List<ContactoEntity>> obtenerPorEmail(@PathVariable String email) {
        List<ContactoEntity> contactos = contactoService.obtenerPorEmail(email);
        return ResponseEntity.ok(contactos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody ContactoEntity contactoActualizado) {
        ContactoEntity contacto = contactoService.actualizar(id, contactoActualizado);
        if (contacto != null) {
            return ResponseEntity.ok(contacto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contacto no encontrado");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (contactoService.eliminar(id)) {
            return ResponseEntity.ok("Contacto eliminado exitosamente");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contacto no encontrado");
    }
}
