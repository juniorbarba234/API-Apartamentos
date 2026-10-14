package com.api.apartamentos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.api.apartamentos.models.PropiedadModel;

public interface IPropiedadRepository extends JpaRepository<PropiedadModel, Long> {
    @org.springframework.data.jpa.repository.EntityGraph(attributePaths = "propietario")
    java.util.List<PropiedadModel> findAll();
    @org.springframework.data.jpa.repository.EntityGraph(attributePaths = "propietario")
    java.util.Optional<PropiedadModel> findById(Long id);
    @org.springframework.data.jpa.repository.EntityGraph(attributePaths = "propietario")
    java.util.List<PropiedadModel> findByTipoIgnoreCase(String tipo);
    @org.springframework.data.jpa.repository.EntityGraph(attributePaths = "propietario")
    java.util.List<PropiedadModel> findByPropietario_Id(Long id);
    
}
