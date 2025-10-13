package com.cgm.vidrieria.Vidrios_CGM.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "compra")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCompra;


    @ManyToOne
    @JoinColumn(name = "id_proveedor")  // clave foránea hacia proveedor
    private Proveedor proveedor;

    @ManyToOne
    @JoinColumn(name = "id_empleado")  // clave foránea hacia empleado
    private Empleado empleado;

    @Column(name = "tipo_comprobante")
    private String tipoComprobante;

    @Column(name = "numero_comprobante")
    private String numeroComprobante;

    @Column(name = "subtotal")
    private BigDecimal subTotal;

    private BigDecimal igv;

    private BigDecimal total;

    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL)
    private List<DetalleCompra> detalles;
}
