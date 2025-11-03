package com.pruebajava.productos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "productos")
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El nombre del producto es obligatorio")
    private String nombre;
    
    @NotBlank(message = "La descripción del producto es obligatoria")
    private String descripcion;
    
    @NotNull(message = "El precio del producto es obligatorio")
    @Positive(message = "El precio debe ser mayor que cero")
    private BigDecimal precio;
    
    private String categoria;
    
    @Column(name = "codigo_sku", unique = true)
    @NotBlank(message = "El código SKU es obligatorio")
    private String codigoSku;
}