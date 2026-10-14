package com.api.apartamentos.services;

import java.util.List;
import org.springframework.stereotype.Service;
import com.api.apartamentos.models.DisponibilidadModel;
import com.api.apartamentos.repositories.IDisponibilidadRepository;

@Service
public class DisponibilidadService extends BaseCrudService<DisponibilidadModel> {
    private final IDisponibilidadRepository disponibilidadRepository;
    public DisponibilidadService(IDisponibilidadRepository repository) { super(repository); disponibilidadRepository = repository; }
    public List<DisponibilidadModel> findByUnidad(Long id) { return disponibilidadRepository.findByUnidad_IdUnidad(id); }
    @Override
    public DisponibilidadModel save(DisponibilidadModel value) {
        if (value.getFechaInicio() == null || value.getFechaFin() == null || !value.getFechaFin().isAfter(value.getFechaInicio())) throw new IllegalArgumentException("El rango de disponibilidad no es válido");
        if (value.getPrecioEspecial() != null && value.getPrecioEspecial().signum() <= 0) throw new IllegalArgumentException("El precio especial debe ser mayor que cero");
        return super.save(value);
    }
}
