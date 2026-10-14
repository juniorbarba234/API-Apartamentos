package com.api.apartamentos.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.api.apartamentos.models.DisponibilidadModel;

public interface IDisponibilidadRepository extends JpaRepository<DisponibilidadModel, Long> {
    @org.springframework.data.jpa.repository.EntityGraph(attributePaths = {"unidad"})
    java.util.List<DisponibilidadModel> findAll();
    @org.springframework.data.jpa.repository.EntityGraph(attributePaths = {"unidad"})
    java.util.Optional<DisponibilidadModel> findById(Long id);

    @org.springframework.data.jpa.repository.EntityGraph(attributePaths = {"unidad"})
    List<DisponibilidadModel> findByUnidad_IdUnidad(Long idUnidad);
}
