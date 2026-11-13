package com.carmotors.modelo;

import java.time.LocalDate;


public class EvaluacionProveedor {

    private int id;
    private int proveedorId;
    private LocalDate fechaEvaluacion;
    private int calificacionCalidad;
    private int calificacionPuntualidad;
    private String observaciones;

    public EvaluacionProveedor() {
    }

    public EvaluacionProveedor(int id, int proveedorId, LocalDate fechaEvaluacion,
                               int calificacionCalidad, int calificacionPuntualidad,
                               String observaciones) {
        this.id = id;
        this.proveedorId = proveedorId;
        this.fechaEvaluacion = fechaEvaluacion;
        this.calificacionCalidad = calificacionCalidad;
        this.calificacionPuntualidad = calificacionPuntualidad;
        this.observaciones = observaciones;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(int proveedorId) {
        this.proveedorId = proveedorId;
    }

    public LocalDate getFechaEvaluacion() {
        return fechaEvaluacion;
    }

    public void setFechaEvaluacion(LocalDate fechaEvaluacion) {
        this.fechaEvaluacion = fechaEvaluacion;
    }

    public int getCalificacionCalidad() {
        return calificacionCalidad;
    }

    public void setCalificacionCalidad(int calificacionCalidad) {
        this.calificacionCalidad = calificacionCalidad;
    }

    public int getCalificacionPuntualidad() {
        return calificacionPuntualidad;
    }

    public void setCalificacionPuntualidad(int calificacionPuntualidad) {
        this.calificacionPuntualidad = calificacionPuntualidad;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }


    public double getPromedio() {
        return (calificacionCalidad + calificacionPuntualidad) / 2.0;
    }

    @Override
    public String toString() {
        return "EvaluacionProveedor{id=" + id
                + ", proveedorId=" + proveedorId
                + ", fecha=" + fechaEvaluacion
                + ", calidad=" + calificacionCalidad
                + ", puntualidad=" + calificacionPuntualidad
                + ", promedio=" + getPromedio() + "}";
    }
}