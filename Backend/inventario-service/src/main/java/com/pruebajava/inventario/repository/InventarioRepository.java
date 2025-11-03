package com.pruebajava.inventario.repository;

import com.pruebajava.inventario.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    Optional<Inventario> findByProductoSku(String productoSku);
    boolean existsByProductoSku(String productoSku);
}