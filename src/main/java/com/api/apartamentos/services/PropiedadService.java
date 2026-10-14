package com.api.apartamentos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.api.apartamentos.models.PropiedadModel;
import com.api.apartamentos.repositories.IPropiedadRepository;

@Service
public class PropiedadService extends BaseCrudService<PropiedadModel> {

    private final com.api.apartamentos.repositories.IClienteRepository clientes;

    public PropiedadService(IPropiedadRepository propiedadRepository, com.api.apartamentos.repositories.IClienteRepository clientes) {
        super(propiedadRepository);
        this.clientes = clientes;
    }

    @Override
    public PropiedadModel save(PropiedadModel propiedad) {
        if (propiedad.getPropietario() == null || propiedad.getPropietario().getId() == null) throw new IllegalArgumentException("El propietario es obligatorio");
        var propietario = clientes.findById(propiedad.getPropietario().getId()).orElseThrow(() -> new IllegalArgumentException("El propietario no existe"));
        if (propietario.getTipo() == com.api.apartamentos.models.ClienteModel.TipoCliente.CLIENTE) throw new IllegalArgumentException("El propietario debe ser PROPIETARIO o EMPRESA");
        propiedad.setPropietario(propietario);
        if (propiedad.getPrecioNoche() == null || propiedad.getPrecioNoche().signum() <= 0) throw new IllegalArgumentException("El precio por noche debe ser mayor que cero");
        if (propiedad.getCapacidad() < 1) throw new IllegalArgumentException("La capacidad debe ser mayor que cero");
        var guardada = super.save(propiedad);
        return repository.findById(guardada.getIdPropiedad()).orElse(guardada);
    }

    public List<PropiedadModel> getAllPropiedades() {
        return findAll();
    }

    public Optional<PropiedadModel> getPropiedadById(Long id) {
        return findById(id);
    }

    public PropiedadModel savePropiedad(PropiedadModel propiedad) {
        return save(propiedad);
    } 
    
    public void deletePropiedad(Long id) {
        deleteById(id);
    }

    public List<PropiedadModel> findByTipo(String tipo) {
        return ((IPropiedadRepository) repository).findByTipoIgnoreCase(tipo);
    }

    public List<PropiedadModel> findByPropietario(Long id) {
        return ((IPropiedadRepository) repository).findByPropietario_Id(id);
    }
}
