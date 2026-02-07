package com.sweetlab.controller;

import com.sweetlab.entity.Pedido;
import com.sweetlab.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    /**
     * GET /api/pedidos
     * Obtiene todos los pedidos
     */
    @GetMapping
    public ResponseEntity<List<Pedido>> obtenerTodos() {
        List<Pedido> pedidos = pedidoService.obtenerTodos();
        return ResponseEntity.ok(pedidos);
    }

    /**
     * GET /api/pedidos/{id}
     * Obtiene un pedido por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtenerPorId(@PathVariable Long id) {
        return pedidoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/pedidos/cliente/{clienteId}
     * Obtiene pedidos de un cliente específico
     */
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Pedido>> obtenerPorCliente(@PathVariable Long clienteId) {
        List<Pedido> pedidos = pedidoService.obtenerPorCliente(clienteId);
        return ResponseEntity.ok(pedidos);
    }

    /**
     * GET /api/pedidos/estado/{estado}
     * Obtiene pedidos por estado
     */
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Pedido>> obtenerPorEstado(@PathVariable String estado) {
        List<Pedido> pedidos = pedidoService.obtenerPorEstado(estado);
        return ResponseEntity.ok(pedidos);
    }

    /**
     * POST /api/pedidos
     * Crea un nuevo pedido
     */
    @PostMapping
    public ResponseEntity<Pedido> crear(@RequestBody Pedido pedido) {
        Pedido pedidoCreado = pedidoService.crear(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoCreado);
    }

    /**
     * PUT /api/pedidos/{id}
     * Actualiza un pedido
     */
    @PutMapping("/{id}")
    public ResponseEntity<Pedido> actualizar(
            @PathVariable Long id,
            @RequestBody Pedido pedidoActualizado) {
        try {
            Pedido pedido = pedidoService.actualizar(id, pedidoActualizado);
            return ResponseEntity.ok(pedido);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * PATCH /api/pedidos/{id}/estado
     * Cambia el estado de un pedido
     */
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Pedido> cambiarEstado(
            @PathVariable Long id,
            @RequestParam String nuevoEstado) {
        try {
            Pedido pedido = pedidoService.cambiarEstado(id, nuevoEstado);
            return ResponseEntity.ok(pedido);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * DELETE /api/pedidos/{id}
     * Elimina un pedido
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        pedidoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
