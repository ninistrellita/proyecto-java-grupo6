package com.carmotors.modelo;

import java.time.LocalDate;

public class Lote {

    private int id;
    private String codigoLote;
    private int repuestoId;
    private int proveedorId;
    private LocalDate fechaIngreso;
    private LocalDate fechaCaducidad;
    private int cantidadRecibida;
    private int cantidadDisponible;

    public Lote() {
    }

    public Lote(int id, String codigoLote, int repuestoId, int proveedorId,
                LocalDate fechaIngreso, LocalDate fechaCaducidad,
                int cantidadRecibida, int cantidadDisponible) {
        this.id = id;
        this.codigoLote = codigoLote;
        this.repuestoId = repuestoId;
        this.proveedorId = proveedorId;
        this.fechaIngreso = fechaIngreso;
        this.fechaCaducidad = fechaCaducidad;
        this.cantidadRecibida = cantidadRecibida;
        this.cantidadDisponible = cantidadDisponible;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigoLote() {
        return codigoLote;
    }

    public void setCodigoLote(String codigoLote) {
        this.codigoLote = codigoLote;
    }

    public int getRepuestoId() {
        return repuestoId;
    }

    public void setRepuestoId(int repuestoId) {
        this.repuestoId = repuestoId;
    }

    public int getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(int proveedorId) {
        this.proveedorId = proveedorId;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public LocalDate getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(LocalDate fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public int getCantidadRecibida() {
        return cantidadRecibida;
    }

    public void setCantidadRecibida(int cantidadRecibida) {
        this.cantidadRecibida = cantidadRecibida;
    }

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }


    public boolean estaProximoACaducar() {
        if (fechaCaducidad == null) {
            return false;
        }
        return !fechaCaducidad.isBefore(LocalDate.now())
                && fechaCaducidad.isBefore(LocalDate.now().plusDays(30));
    }

    public boolean estaCaducado() {
        if (fechaCaducidad == null) {
            return false;
        }
        return fechaCaducidad.isBefore(LocalDate.now());
    }

    @Override
    public String toString() {
        return "Lote{id=" + id
                + ", codigoLote='" + codigoLote + "'"
                + ", repuestoId=" + repuestoId
                + ", proveedorId=" + proveedorId
                + ", fechaIngreso=" + fechaIngreso
                + ", fechaCaducidad=" + fechaCaducidad
                + ", cantidadDisponible=" + cantidadDisponible + "}";
    }
}