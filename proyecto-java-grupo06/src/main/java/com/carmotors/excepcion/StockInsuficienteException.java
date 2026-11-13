package com.carmotors.excepcion;


public class StockInsuficienteException extends RuntimeException {

    public StockInsuficienteException(String mensaje) {
        super(mensaje);
    }

    public StockInsuficienteException(int repuestoId, int solicitado, int disponible) {
        super(String.format(
                "Stock insuficiente para el repuesto id=%d. Solicitado: %d, Disponible: %d",
                repuestoId, solicitado, disponible));
    }
}