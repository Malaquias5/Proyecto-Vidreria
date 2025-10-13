package com.cgm.vidrieria.Vidrios_CGM.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "empleado")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmpleado;

    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private String direccion;
    private String cargo;
    private String usuario;
    private String contrasena;
    private Boolean estado;
}

