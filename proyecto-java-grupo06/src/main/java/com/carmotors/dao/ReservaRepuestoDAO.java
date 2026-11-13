package com.carmotors.dao;

import com.carmotors.modelo.ReservaRepuesto;
import java.util.List;
import java.util.Optional;

public interface ReservaRepuestoDAO {

    void guardar(ReservaRepuesto reserva);

    void actualizar(ReservaRepuesto reserva);

    Optional<ReservaRepuesto> buscarPorId(int id);

    List<ReservaRepuesto> listarPorServicio(int servicioId);

    List<ReservaRepuesto> listarActivasPorRepuesto(int repuestoId);

    void marcarComoDescontadas(int servicioId);

    void eliminar(int id);
}