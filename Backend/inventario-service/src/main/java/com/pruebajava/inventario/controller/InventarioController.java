package com.pruebajava.inventario.controller;

import com.pruebajava.inventario.api.JsonApiResponse;
import com.pruebajava.inventario.model.Inventario;
import com.pruebajava.inventario.service.InventarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventario")
@RequiredArgsConstructor
public class InventarioController {
    
    private final InventarioService inventarioService;
    
    @GetMapping
    public ResponseEntity<JsonApiResponse> getAllInventario() {
        return ResponseEntity.ok(new JsonApiResponse(inventarioService.findAll(), null, null));
    }
    
    @GetMapping("/{productoSku}")
    public ResponseEntity<JsonApiResponse> getInventarioByProductoSku(@PathVariable String productoSku) {
        return ResponseEntity.ok(new JsonApiResponse(inventarioService.findByProductoSku(productoSku), null, null));
    }
    
    @PostMapping
    public ResponseEntity<JsonApiResponse> createInventario(@Valid @RequestBody Inventario inventario) {
        Inventario saved = inventarioService.save(inventario);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new JsonApiResponse(saved, null, null));
    }
    
    @PutMapping("/{productoSku}/cantidad")
    public ResponseEntity<JsonApiResponse> actualizarCantidad(
            @PathVariable String productoSku,
            @RequestParam Integer cantidad) {
        Inventario updated = inventarioService.actualizarCantidad(productoSku, cantidad);
        return ResponseEntity.ok(new JsonApiResponse(updated, null, null));
    }
    
    @PutMapping("/{productoSku}/incrementar")
    public ResponseEntity<JsonApiResponse> incrementarCantidad(
            @PathVariable String productoSku,
            @RequestParam Integer cantidad) {
        Inventario updated = inventarioService.incrementarCantidad(productoSku, cantidad);
        return ResponseEntity.ok(new JsonApiResponse(updated, null, null));
    }
    
    @PutMapping("/{productoSku}/decrementar")
    public ResponseEntity<JsonApiResponse> decrementarCantidad(
            @PathVariable String productoSku,
            @RequestParam Integer cantidad) {
        Inventario updated = inventarioService.decrementarCantidad(productoSku, cantidad);
        return ResponseEntity.ok(new JsonApiResponse(updated, null, null));
    }
    
    @DeleteMapping("/{productoSku}")
    public ResponseEntity<JsonApiResponse> deleteInventario(@PathVariable String productoSku) {
        inventarioService.deleteByProductoSku(productoSku);
        return ResponseEntity.noContent().build();
    }
}