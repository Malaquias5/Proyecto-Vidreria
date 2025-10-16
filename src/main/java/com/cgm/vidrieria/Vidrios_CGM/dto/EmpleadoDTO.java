package com.cgm.vidrieria.Vidrios_CGM.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class EmpleadoDTO {
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
