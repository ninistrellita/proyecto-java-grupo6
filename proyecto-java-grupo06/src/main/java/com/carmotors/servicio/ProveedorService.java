package com.carmotors.servicio;

import com.carmotors.dao.EvaluacionProveedorDAO;
import com.carmotors.dao.ProveedorDAO;
import com.carmotors.dao.RepuestoDAO;
import com.carmotors.excepcion.EntidadNoEncontradaException;
import com.carmotors.modelo.EvaluacionProveedor;
import com.carmotors.modelo.Proveedor;
import com.carmotors.modelo.Repuesto;

import java.util.List;

public class ProveedorService {

    private final ProveedorDAO proveedorDAO;
    private final RepuestoDAO repuestoDAO;
    private final EvaluacionProveedorDAO evaluacionDAO;

    public ProveedorService(ProveedorDAO proveedorDAO,
                            RepuestoDAO repuestoDAO,
                            EvaluacionProveedorDAO evaluacionDAO) {
        this.proveedorDAO = proveedorDAO;
        this.repuestoDAO = repuestoDAO;
        this.evaluacionDAO = evaluacionDAO;
    }

    public void registrarProveedor(Proveedor proveedor) {
        if (proveedor.getNombre() == null || proveedor.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del proveedor es obligatorio");
        }
        proveedorDAO.guardar(proveedor);
    }

    public Proveedor obtenerProveedor(int id) {
        return proveedorDAO.buscarPorId(id)
                .orElseThrow(() -> new EntidadNoEncontradaException("Proveedor", id));
    }

    public List<Proveedor> listarProveedores() {
        return proveedorDAO.listarTodos();
    }

    public List<Repuesto> obtenerCatalogo(int proveedorId) {
        obtenerProveedor(proveedorId);
        return repuestoDAO.listarPorProveedor(proveedorId);
    }

    public void registrarEvaluacion(EvaluacionProveedor evaluacion) {
        if (evaluacion.getCalificacionCalidad() < 1 || evaluacion.getCalificacionCalidad() > 5) {
            throw new IllegalArgumentException("La calificación de calidad debe estar entre 1 y 5");
        }
        if (evaluacion.getCalificacionPuntualidad() < 1 || evaluacion.getCalificacionPuntualidad() > 5) {
            throw new IllegalArgumentException("La calificación de puntualidad debe estar entre 1 y 5");
        }
        obtenerProveedor(evaluacion.getProveedorId());
        evaluacionDAO.guardar(evaluacion);
    }

    public List<EvaluacionProveedor> listarEvaluaciones(int proveedorId) {
        return evaluacionDAO.listarPorProveedor(proveedorId);
    }
}