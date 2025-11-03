package com.pruebajava.inventario.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Clase de configuración mínima para preparar JWT.
 * Aquí se pueden añadir beans de JwtDecoder/Encoder o filtros cuando se implemente la seguridad completa.
 */
@Configuration
public class JwtConfig {

    private final JwtProperties jwtProperties;

    public JwtConfig(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Bean
    public JwtProperties jwtProperties() {
        return jwtProperties;
    }
}
