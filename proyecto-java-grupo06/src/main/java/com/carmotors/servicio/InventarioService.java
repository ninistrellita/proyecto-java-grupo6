package com.carmotors.servicio;

import com.carmotors.dao.LoteDAO;
import com.carmotors.dao.RepuestoDAO;
import com.carmotors.dao.ReservaRepuestoDAO;
import com.carmotors.excepcion.EntidadNoEncontradaException;
import com.carmotors.excepcion.StockInsuficienteException;
import com.carmotors.modelo.Lote;
import com.carmotors.modelo.Repuesto;
import com.carmotors.modelo.ReservaRepuesto;
import com.carmotors.modelo.ReservaRepuesto.EstadoReserva;

import java.time.LocalDateTime;
import java.util.List;

public class InventarioService {

    private final RepuestoDAO repuestoDAO;
    private final LoteDAO loteDAO;
    private final ReservaRepuestoDAO reservaDAO;

    public InventarioService(RepuestoDAO repuestoDAO, LoteDAO loteDAO, ReservaRepuestoDAO reservaDAO) {
        this.repuestoDAO = repuestoDAO;
        this.loteDAO = loteDAO;
        this.reservaDAO = reservaDAO;
    }


    public void registrarRepuesto(Repuesto repuesto) {
        if (repuesto.getNombre() == null || repuesto.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del repuesto es obligatorio");
        }
        if (repuesto.getStockMinimo() < 0) {
            throw new IllegalArgumentException("El stock mínimo no puede ser negativo");
        }
        repuestoDAO.guardar(repuesto);
    }

    public Repuesto obtenerRepuesto(int id) {
        return repuestoDAO.buscarPorId(id)
                .orElseThrow(() -> new EntidadNoEncontradaException("Repuesto", id));
    }

    public List<Repuesto> listarRepuestos() {
        return repuestoDAO.listarTodos();
    }

    public List<Repuesto> listarRepuestosBajoStock() {
        return repuestoDAO.listarBajoStockMinimo();
    }


    public void registrarLote(Lote lote) {
        if (lote.getCodigoLote() == null || lote.getCodigoLote().isBlank()) {
            throw new IllegalArgumentException("El código de lote es obligatorio");
        }
        if (lote.getCantidadRecibida() <= 0) {
            throw new IllegalArgumentException("La cantidad recibida debe ser mayor a cero");
        }
        lote.setCantidadDisponible(lote.getCantidadRecibida());
        loteDAO.guardar(lote);

    }

    public List<Lote> listarLotesPorRepuesto(int repuestoId) {
        return loteDAO.listarPorRepuesto(repuestoId);
    }

    public ReservaRepuesto reservar(int repuestoId, int cantidad, int servicioId) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad a reservar debe ser mayor a cero");
        }

        Repuesto repuesto = obtenerRepuesto(repuestoId);

        if (!repuesto.tieneStockSuficiente(cantidad)) {
            throw new StockInsuficienteException(repuestoId, cantidad, repuesto.getCantidadStock());
        }

        ReservaRepuesto reserva = new ReservaRepuesto();
        reserva.setRepuestoId(repuestoId);
        reserva.setServicioId(servicioId);
        reserva.setCantidad(cantidad);
        reserva.setEstado(EstadoReserva.RESERVADO);
        reserva.setFechaReserva(LocalDateTime.now());

        reservaDAO.guardar(reserva);


        return reserva;
    }

    public void descontarStock(int servicioId) {
        List<ReservaRepuesto> reservas = reservaDAO.listarPorServicio(servicioId);

        for (ReservaRepuesto reserva : reservas) {
            if (reserva.getEstado() != EstadoReserva.RESERVADO) {
                continue; // evitar descontar dos veces
            }

            Repuesto repuesto = obtenerRepuesto(reserva.getRepuestoId());
            int nuevoStock = repuesto.getCantidadStock() - reserva.getCantidad();

            if (nuevoStock < 0) {
                // Situación anómala: se reservó más de lo que realmente había
                throw new StockInsuficienteException(
                        reserva.getRepuestoId(),
                        reserva.getCantidad(),
                        repuesto.getCantidadStock());
            }

            repuestoDAO.actualizarStock(repuesto.getId(), nuevoStock);
            reserva.setEstado(EstadoReserva.DESCONTADO);
            reservaDAO.actualizar(reserva);
        }
    }

    public void cancelarReservas(int servicioId) {
        List<ReservaRepuesto> reservas = reservaDAO.listarPorServicio(servicioId);
        for (ReservaRepuesto reserva : reservas) {
            if (reserva.getEstado() == EstadoReserva.RESERVADO) {
                reserva.setEstado(EstadoReserva.CANCELADO);
                reservaDAO.actualizar(reserva);
            }
        }
    }
}