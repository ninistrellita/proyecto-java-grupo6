package com.carmotors.dao;

import com.carmotors.modelo.Lote;
import java.util.List;
import java.util.Optional;


public interface LoteDAO {

    void guardar(Lote lote);

    void actualizar(Lote lote);

    Optional<Lote> buscarPorId(int id);

    Optional<Lote> buscarPorCodigo(String codigoLote);

    List<Lote> listarPorRepuesto(int repuestoId);

    List<Lote> listarPorProveedor(int proveedorId);


    List<Lote> listarProximosACaducar();

    void eliminar(int id);
}