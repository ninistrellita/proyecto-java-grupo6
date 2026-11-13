
package com.carmotors.modelo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrdenServicio {

    private int id;
    private int vehiculoId;
    private int mantenimientoTipoId;
    private LocalDateTime fechaRecepcion;
    private LocalDateTime fechaEntrega;
    private String descripcionProblema;
    private BigDecimal costoManoObra;
    private int estadoId;

    public OrdenServicio(
            int vehiculoId,
            int mantenimientoTipoId,
            String descripcionProblema,
            BigDecimal costoManoObra,
            int estadoId) {

        this.vehiculoId = vehiculoId;
        this.mantenimientoTipoId = mantenimientoTipoId;
        this.fechaRecepcion = LocalDateTime.now();
        this.descripcionProblema = descripcionProblema;
        this.costoManoObra = costoManoObra;
        this.estadoId = estadoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVehiculoId() {
        return vehiculoId;
    }

    public int getMantenimientoTipoId() {
        return mantenimientoTipoId;
    }

    public LocalDateTime getFechaRecepcion() {
        return fechaRecepcion;
    }

    public LocalDateTime getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDateTime fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getDescripcionProblema() {
        return descripcionProblema;
    }

    public BigDecimal getCostoManoObra() {
        return costoManoObra;
    }

    public int getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(int estadoId) {
        this.estadoId = estadoId;
    }
}
