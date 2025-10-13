package com.cgm.vidrieria.Vidrios_CGM.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long idCliente;

    private String nombre;
    private String apellido;
    @Column(name = "tipo_documento")
    private String tipoDocumento;
    private String documento;
    private String telefono;
    private String direccion;
    private String email;

    @Column(name = "tipo_cliente")
    private String tipoCliente;
}
