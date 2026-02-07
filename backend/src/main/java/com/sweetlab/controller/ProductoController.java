package com.sweetlab.controller;

import com.sweetlab.entity.Producto;
import com.sweetlab.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    /**
     * GET /api/productos
     * Obtiene todos los productos
     */
    @GetMapping
    public ResponseEntity<List<Producto>> obtenerTodos() {
        List<Producto> productos = productoService.obtenerTodos();
        return ResponseEntity.ok(productos);
    }

    /**
     * GET /api/productos/{id}
     * Obtiene un producto por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Long id) {
        return productoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/productos/categoria/{categoria}
     * Obtiene productos por categoría
     */
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Producto>> obtenerPorCategoria(@PathVariable String categoria) {
        List<Producto> productos = productoService.obtenerPorCategoria(categoria);
        return ResponseEntity.ok(productos);
    }

    /**
     * GET /api/productos/disponibles
     * Obtiene solo productos disponibles
     */
    @GetMapping("/disponibles")
    public ResponseEntity<List<Producto>> obtenerDisponibles() {
        List<Producto> productos = productoService.obtenerDisponibles();
        return ResponseEntity.ok(productos);
    }

    /**
     * POST /api/productos
     * Crea un nuevo producto
     */
    @PostMapping
    public ResponseEntity<Producto> crear(@RequestBody Producto producto) {
        Producto productoCreado = productoService.crear(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoCreado);
    }

    /**
     * PUT /api/productos/{id}
     * Actualiza un producto
     */
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(
            @PathVariable Long id,
            @RequestBody Producto productoActualizado) {
        try {
            Producto producto = productoService.actualizar(id, productoActualizado);
            return ResponseEntity.ok(producto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * DELETE /api/productos/{id}
     * Elimina un producto
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
