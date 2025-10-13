package com.cgm.vidrieria.Vidrios_CGM.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "proveedor")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProveedor;

    private String razonSocial;
    private String ruc;
    private String telefono;
    private String direccion;
    private String email;
    private String contacto;
}

