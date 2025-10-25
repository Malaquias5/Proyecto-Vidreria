package com.cgm.vidrieria.Vidrios_CGM.dto;

import java.util.List;

public class VentaCreateDTO {
    private Long clienteId;
    private Long vendedorId;
    private String tipoPago;
    private List<DetalleVentaCreateDTO> items;

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getVendedorId() {
        return vendedorId;
    }

    public void setVendedorId(Long vendedorId) {
        this.vendedorId = vendedorId;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }

    public List<DetalleVentaCreateDTO> getItems() {
        return items;
    }

    public void setItems(List<DetalleVentaCreateDTO> items) {
        this.items = items;
    }
}
