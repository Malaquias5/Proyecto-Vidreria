package com.cgm.vidrieria.Vidrios_CGM.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "inventario")
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInventario;

    @ManyToOne
    @JoinColumn(name = "idProducto")
    private Producto producto;

    private String tipoMovimiento; // 'entrada' o 'salida'
    private Integer cantidad;
    private LocalDateTime fecha;
    private String referencia;
}

