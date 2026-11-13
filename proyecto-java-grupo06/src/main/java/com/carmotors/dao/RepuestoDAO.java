package com.carmotors.dao;

import com.carmotors.modelo.Repuesto;
import java.util.List;
import java.util.Optional;


public interface RepuestoDAO {

    void guardar(Repuesto repuesto);

    void actualizar(Repuesto repuesto);

    Optional<Repuesto> buscarPorId(int id);

    List<Repuesto> listarTodos();

    List<Repuesto> listarPorProveedor(int proveedorId);

    List<Repuesto> listarBajoStockMinimo();


    void actualizarStock(int repuestoId, int nuevaCantidad);

    void eliminar(int id);
}