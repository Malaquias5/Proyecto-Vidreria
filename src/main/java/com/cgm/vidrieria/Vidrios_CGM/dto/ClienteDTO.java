package com.cgm.vidrieria.Vidrios_CGM.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ClienteDTO {
    private Long idCliente;
    private String nombre;
    private String apellido;
    private String tipoDocumento;
    private String documento;
    private String telefono;
    private String direccion;
    private String email;
    private String tipoCliente;
}
