package com.pruebajava.inventario.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Wrapper simple para devolver respuestas en formato JSON:API básico.
 * data puede ser un objeto o una lista de recursos.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonApiResponse {
    private Object data;
    private Map<String, Object> meta;
    private List<Object> included;
}
