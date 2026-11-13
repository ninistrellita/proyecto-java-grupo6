package com.carmotors.dao;

import com.carmotors.modelo.EvaluacionProveedor;
import java.util.List;
import java.util.Optional;


public interface EvaluacionProveedorDAO {

    void guardar(EvaluacionProveedor evaluacion);

    void actualizar(EvaluacionProveedor evaluacion);

    Optional<EvaluacionProveedor> buscarPorId(int id);

    List<EvaluacionProveedor> listarPorProveedor(int proveedorId);


    Optional<EvaluacionProveedor> buscarUltimaPorProveedor(int proveedorId);

    void eliminar(int id);
}