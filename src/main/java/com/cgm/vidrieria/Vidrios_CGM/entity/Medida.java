package com.cgm.vidrieria.Vidrios_CGM.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "medida")
public class Medida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMedida;

    private String descripcion;
    private Double alto;
    private Double ancho;
    private Double grosor;
}

