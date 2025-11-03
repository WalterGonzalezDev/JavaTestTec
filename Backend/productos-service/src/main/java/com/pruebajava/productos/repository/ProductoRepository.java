package com.pruebajava.productos.repository;

import com.pruebajava.productos.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Optional<Producto> findByCodigoSku(String codigoSku);
    boolean existsByCodigoSku(String codigoSku);
}