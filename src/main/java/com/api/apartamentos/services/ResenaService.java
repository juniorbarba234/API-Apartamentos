package com.api.apartamentos.services;

import java.util.List;
import org.springframework.stereotype.Service;
import com.api.apartamentos.models.ResenaModel;
import com.api.apartamentos.repositories.IResenaRepository;

@Service
public class ResenaService extends BaseCrudService<ResenaModel> {
    private final IResenaRepository resenaRepository;
    private final com.api.apartamentos.repositories.IReservaRepository reservas;
    public ResenaService(IResenaRepository repository, com.api.apartamentos.repositories.IReservaRepository reservas) {
        super(repository);
        resenaRepository = repository;
        this.reservas = reservas;
    }
    @Override
    public ResenaModel save(ResenaModel value) {
        if (value.getReserva() == null || value.getReserva().getIdReservacion() == null) throw new IllegalArgumentException("La reserva es obligatoria");
        var reserva = reservas.findById(value.getReserva().getIdReservacion()).orElseThrow(() -> new IllegalArgumentException("La reserva no existe"));
        if (value.getCliente() == null || !reserva.getCliente().getId().equals(value.getCliente().getId())) throw new IllegalArgumentException("La reseña debe pertenecer al cliente de la reserva");
        value.setReserva(reserva);
        value.setCliente(reserva.getCliente());
        return super.save(value);
    }
    public List<ResenaModel> findByReserva(Long id) { return resenaRepository.findByReserva_IdReservacion(id); }
}
