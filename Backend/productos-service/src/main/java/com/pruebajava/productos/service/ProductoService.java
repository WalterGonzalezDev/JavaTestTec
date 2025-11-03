package com.pruebajava.productos.service;

import com.pruebajava.productos.exception.ProductoNotFoundException;
import com.pruebajava.productos.model.Producto;
import com.pruebajava.productos.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoService {
    
    private final ProductoRepository productoRepository;
    
    @Transactional(readOnly = true)
    public List<Producto> findAll() {
        return productoRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Producto findById(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException(id));
    }
    
    @Transactional
    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }
    
    @Transactional
    public Producto update(Long id, Producto producto) {
        Producto existingProducto = findById(id);
        
        existingProducto.setNombre(producto.getNombre());
        existingProducto.setDescripcion(producto.getDescripcion());
        existingProducto.setPrecio(producto.getPrecio());
        existingProducto.setCategoria(producto.getCategoria());
        existingProducto.setCodigoSku(producto.getCodigoSku());
        
        return productoRepository.save(existingProducto);
    }
    
    @Transactional
    public void deleteById(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new ProductoNotFoundException(id);
        }
        productoRepository.deleteById(id);
    }
}