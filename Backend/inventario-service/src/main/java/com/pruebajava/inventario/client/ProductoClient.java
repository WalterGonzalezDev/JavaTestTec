package com.pruebajava.inventario.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ProductoClient {
    
    private final RestTemplate restTemplate;
    
    @Value("${servicios.productos.url}")
    private String productosServiceUrl;
    
    public boolean existeProducto(String sku) {
        try {
            String url = productosServiceUrl + "/api/productos/sku/" + sku;
            restTemplate.getForObject(url, Object.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}