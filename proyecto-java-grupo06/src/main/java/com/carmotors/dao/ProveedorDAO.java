package com.carmotors.dao;

import com.carmotors.modelo.Proveedor;
import java.util.List;
import java.util.Optional;


public interface ProveedorDAO {

    void guardar(Proveedor proveedor);

    void actualizar(Proveedor proveedor);

    Optional<Proveedor> buscarPorId(int id);

    List<Proveedor> listarTodos();

    void eliminar(int id);
}