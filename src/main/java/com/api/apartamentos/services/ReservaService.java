package com.api.apartamentos.services;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.api.apartamentos.models.ReservaModel;
import com.api.apartamentos.repositories.IReservaRepository;
import com.api.apartamentos.repositories.IUnidadRentableRepository;
import com.api.apartamentos.repositories.IClienteRepository;

@Service
public class ReservaService extends BaseCrudService<ReservaModel> {
    private final IReservaRepository reservaRepository;
    private final IUnidadRentableRepository unidades;
    private final IClienteRepository clientes;

    public ReservaService(IReservaRepository repository, IUnidadRentableRepository unidades, IClienteRepository clientes) {
        super(repository);
        reservaRepository = repository;
        this.unidades = unidades;
        this.clientes = clientes;
    }

    @Override
    @Transactional
    public ReservaModel save(ReservaModel reserva) {
        if (reserva.getUnidad() == null || reserva.getUnidad().getIdUnidad() == null) throw new IllegalArgumentException("La unidad es obligatoria");
        if (reserva.getCliente() == null || reserva.getCliente().getId() == null) throw new IllegalArgumentException("El cliente es obligatorio");
        var unidad = unidades.findById(reserva.getUnidad().getIdUnidad()).orElseThrow(() -> new IllegalArgumentException("La unidad no existe"));
        var cliente = clientes.findById(reserva.getCliente().getId()).orElseThrow(() -> new IllegalArgumentException("El cliente no existe"));
        reserva.setUnidad(unidad);
        reserva.setCliente(cliente);
        if (reserva.getEstado() == null || !java.util.Set.of("PENDIENTE", "CONFIRMADA", "CANCELADA", "RECHAZADA", "COMPLETADA").contains(reserva.getEstado())) throw new IllegalArgumentException("Estado de reserva no válido");
        if (!java.util.Set.of("CANCELADA", "RECHAZADA").contains(reserva.getEstado())) {
            if (!"DISPONIBLE".equals(unidad.getEstado())) throw new IllegalArgumentException("La unidad no está disponible");
            if (cliente.getEstatus() != com.api.apartamentos.models.ClienteModel.ClienteStatus.ACTIVO) throw new IllegalArgumentException("El cliente no está activo");
        }
        if (reserva.getFechaEntrada() == null || reserva.getFechaSalida() == null || !reserva.getFechaSalida().isAfter(reserva.getFechaEntrada())) throw new IllegalArgumentException("El rango de fechas no es válido");
        if (!java.util.Set.of("CANCELADA", "RECHAZADA").contains(reserva.getEstado()) && reservaRepository.existeSolapamiento(reserva.getUnidad().getIdUnidad(), reserva.getIdReservacion(), reserva.getFechaEntrada(), reserva.getFechaSalida())) throw new IllegalArgumentException("La unidad ya está reservada en ese rango");
        long noches = ChronoUnit.DAYS.between(reserva.getFechaEntrada().toLocalDate(), reserva.getFechaSalida().toLocalDate());
        if (noches < 1) throw new IllegalArgumentException("La reserva debe incluir al menos una noche");
        reserva.setImporteTotal(unidad.getPrecioNoche().multiply(BigDecimal.valueOf(noches)));
        return reservaRepository.save(reserva);
    }
}
