package com.pruebajava.productos.controller;

import com.pruebajava.productos.model.Producto;
import com.pruebajava.productos.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {
    
    private final ProductoService productoService;
    
    @GetMapping
    public ResponseEntity<List<Producto>> getAllProductos() {
        return ResponseEntity.ok(productoService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.findById(id));
    }
    
    @PostMapping
    public ResponseEntity<Producto> createProducto(@Valid @RequestBody Producto producto) {
        Producto savedProducto = productoService.save(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProducto);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Producto> updateProducto(
            @PathVariable Long id,
            @Valid @RequestBody Producto producto) {
        return ResponseEntity.ok(productoService.update(id, producto));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        productoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}