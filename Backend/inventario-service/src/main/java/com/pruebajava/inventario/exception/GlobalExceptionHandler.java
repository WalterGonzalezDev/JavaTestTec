package com.pruebajava.inventario.exception;

import com.pruebajava.inventario.api.JsonApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.UUID;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InventarioNotFoundException.class)
    public ResponseEntity<Object> handleNotFound(InventarioNotFoundException ex) {
        JsonApiError err = new JsonApiError(UUID.randomUUID().toString(), "404", "INVENTORY_NOT_FOUND",
                "Inventario no encontrado", ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap("errors", Collections.singletonList(err)));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleBadRequest(IllegalArgumentException ex) {
        JsonApiError err = new JsonApiError(UUID.randomUUID().toString(), "400", "INVALID_ARGUMENT",
                "Argumento inválido", ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap("errors", Collections.singletonList(err)));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Object> handleConflict(IllegalStateException ex) {
        JsonApiError err = new JsonApiError(UUID.randomUUID().toString(), "409", "CONFLICT",
                "Conflicto de estado", ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap("errors", Collections.singletonList(err)));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAll(Exception ex) {
        JsonApiError err = new JsonApiError(UUID.randomUUID().toString(), "500", "INTERNAL_ERROR",
                "Error interno", ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Collections.singletonMap("errors", Collections.singletonList(err)));
    }
}
