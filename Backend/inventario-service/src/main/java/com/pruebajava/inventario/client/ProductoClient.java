package com.pruebajava.inventario.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ProductoClient {
    
    private final RestTemplate restTemplate;
    
    @Value("${servicios.productos.url}")
    private String productosServiceUrl;
    
    @Value("${servicios.productos.api-key}")
    private String productosApiKey;
    
    public boolean existeProducto(String sku) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-API-Key", productosApiKey);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            
            String url = productosServiceUrl + "/api/productos/sku/" + sku;
            restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}