package com.sweetlab.controller;

import com.sweetlab.entity.Cliente;
import com.sweetlab.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    /**
     * GET /api/clientes
     * Obtiene todos los clientes
     */
    @GetMapping
    public ResponseEntity<List<Cliente>> obtenerTodos() {
        List<Cliente> clientes = clienteService.obtenerTodos();
        return ResponseEntity.ok(clientes);
    }

    /**
     * GET /api/clientes/{id}
     * Obtiene un cliente por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerPorId(@PathVariable Long id) {
        return clienteService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/clientes/activos
     * Obtiene solo clientes activos
     */
    @GetMapping("/activos")
    public ResponseEntity<List<Cliente>> obtenerActivos() {
        List<Cliente> clientes = clienteService.obtenerActivos();
        return ResponseEntity.ok(clientes);
    }

    /**
     * GET /api/clientes/buscar/{nombre}
     * Busca clientes por nombre
     */
    @GetMapping("/buscar/{nombre}")
    public ResponseEntity<List<Cliente>> buscarPorNombre(@PathVariable String nombre) {
        List<Cliente> clientes = clienteService.buscarPorNombre(nombre);
        return ResponseEntity.ok(clientes);
    }

    /**
     * POST /api/clientes
     * Crea un nuevo cliente
     */
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Cliente cliente) {
        try {
            Cliente clienteCreado = clienteService.crear(cliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(clienteCreado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * PUT /api/clientes/{id}
     * Actualiza un cliente
     */
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizar(
            @PathVariable Long id,
            @RequestBody Cliente clienteActualizado) {
        try {
            Cliente cliente = clienteService.actualizar(id, clienteActualizado);
            return ResponseEntity.ok(cliente);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * DELETE /api/clientes/{id}
     * Elimina un cliente
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
