package com.cgm.vidrieria.Vidrios_CGM.exception;

public class StockInsuficienteException extends RuntimeException {
    private Long productoId;
    private Integer disponible;

    public StockInsuficienteException(Long productoId, Integer disponible) {
        super("Stock insuficiente para producto id: " + productoId + ", disponible: " + disponible);
        this.productoId = productoId;
        this.disponible = disponible;
    }

    public Long getProductoId() { return productoId; }
    public Integer getDisponible() { return disponible; }
}

