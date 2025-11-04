package com.pruebajava.inventario.service;

import com.pruebajava.inventario.exception.InventarioNotFoundException;
import com.pruebajava.inventario.model.Inventario;
import com.pruebajava.inventario.repository.InventarioRepository;
import lombok.RequiredArgsConstructor;
import com.pruebajava.inventario.client.ProductoClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventarioService {
    
    private final InventarioRepository inventarioRepository;
    private final ProductoClient productoClient;
    private static final Logger log = LoggerFactory.getLogger(InventarioService.class);
    
    @Transactional(readOnly = true)
    public List<Inventario> findAll() {
        return inventarioRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Inventario findByProductoSku(String productoSku) {
        return inventarioRepository.findByProductoSku(productoSku)
                .orElseThrow(() -> new InventarioNotFoundException(productoSku, "No existe registro de inventario"));
    }
    
    @Transactional
    public Inventario save(Inventario inventario) {
        if (!productoClient.existeProducto(inventario.getProductoSku())) {
            throw new IllegalArgumentException("El producto con SKU " + inventario.getProductoSku() + " no existe");
        }
        log.info("[event] inventario.created sku={} cantidad={}", inventario.getProductoSku(), inventario.getCantidad());
        return inventarioRepository.save(inventario);
    }
    
    @Transactional
    public Inventario actualizarCantidad(String productoSku, Integer cantidad) {
        Inventario inventario = findByProductoSku(productoSku);
        inventario.setCantidad(cantidad);
        log.info("[event] inventario.updated sku={} cantidad={}", productoSku, cantidad);
        return inventarioRepository.save(inventario);
    }
    
    @Transactional
    public Inventario incrementarCantidad(String productoSku, Integer cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad a incrementar debe ser mayor que cero");
        }
        
        Inventario inventario = findByProductoSku(productoSku);
        inventario.setCantidad(inventario.getCantidad() + cantidad);
        log.info("[event] inventario.increment sku={} incremento={} nuevaCantidad={}", productoSku, cantidad, inventario.getCantidad());
        return inventarioRepository.save(inventario);
    }
    
    @Transactional
    public Inventario decrementarCantidad(String productoSku, Integer cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad a decrementar debe ser mayor que cero");
        }
        
        Inventario inventario = findByProductoSku(productoSku);
        if (inventario.getCantidad() < cantidad) {
            throw new IllegalStateException("No hay suficiente stock disponible");
        }
        
        inventario.setCantidad(inventario.getCantidad() - cantidad);
        log.info("[event] inventario.decrement sku={} decremento={} nuevaCantidad={}", productoSku, cantidad, inventario.getCantidad());
        return inventarioRepository.save(inventario);
    }
    
    @Transactional
    public void deleteByProductoSku(String productoSku) {
        Inventario inventario = findByProductoSku(productoSku);
        log.info("[event] inventario.deleted sku={}", productoSku);
        inventarioRepository.delete(inventario);
    }
}