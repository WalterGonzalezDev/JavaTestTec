package com.pruebajava.productos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductoNotFoundException extends RuntimeException {
    public ProductoNotFoundException(String mensaje) {
        super(mensaje);
    }
    
    public ProductoNotFoundException(Long id) {
        super("No se encontró el producto con ID: " + id);
    }
}