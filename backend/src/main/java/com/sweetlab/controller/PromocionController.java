package com.sweetlab.controller;

import com.sweetlab.entity.Promocion;
import com.sweetlab.service.PromocionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/promociones")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class PromocionController {

    @Autowired
    private PromocionService promocionService;

    /**
     * GET /api/promociones
     * Obtiene todas las promociones
     */
    @GetMapping
    public ResponseEntity<List<Promocion>> obtenerTodas() {
        List<Promocion> promociones = promocionService.obtenerTodas();
        return ResponseEntity.ok(promociones);
    }

    /**
     * GET /api/promociones/{id}
     * Obtiene una promoción por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Promocion> obtenerPorId(@PathVariable Long id) {
        return promocionService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/promociones/activas
     * Obtiene solo promociones activas
     */
    @GetMapping("/activas")
    public ResponseEntity<List<Promocion>> obtenerActivas() {
        List<Promocion> promociones = promocionService.obtenerActivas();
        return ResponseEntity.ok(promociones);
    }

    /**
     * GET /api/promociones/codigo/{codigo}
     * Obtiene una promoción por código
     */
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Promocion> obtenerPorCodigo(@PathVariable String codigo) {
        return promocionService.obtenerPorCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/promociones
     * Crea una nueva promoción
     */
    @PostMapping
    public ResponseEntity<Promocion> crear(@RequestBody Promocion promocion) {
        Promocion promocionCreada = promocionService.crear(promocion);
        return ResponseEntity.status(HttpStatus.CREATED).body(promocionCreada);
    }

    /**
     * PUT /api/promociones/{id}
     * Actualiza una promoción
     */
    @PutMapping("/{id}")
    public ResponseEntity<Promocion> actualizar(
            @PathVariable Long id,
            @RequestBody Promocion promocionActualizada) {
        try {
            Promocion promocion = promocionService.actualizar(id, promocionActualizada);
            return ResponseEntity.ok(promocion);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * DELETE /api/promociones/{id}
     * Elimina una promoción
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        promocionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
