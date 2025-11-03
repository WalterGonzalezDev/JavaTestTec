package com.pruebajava.inventario.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InventarioNotFoundException extends RuntimeException {
    public InventarioNotFoundException(String mensaje) {
        super(mensaje);
    }
    
    public InventarioNotFoundException(String productoSku, String mensaje) {
        super("No se encontró el inventario para el producto SKU: " + productoSku + ". " + mensaje);
    }
}