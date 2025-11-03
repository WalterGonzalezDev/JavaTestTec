package com.pruebajava.inventario.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonApiError {
    private String id;
    private String status;
    private String code;
    private String title;
    private String detail;
    private Map<String, Object> source;
}
