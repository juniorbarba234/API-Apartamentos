package com.api.apartamentos.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.api.apartamentos.models.PropiedadImagenModel;

public interface IPropiedadImagenRepository extends JpaRepository<PropiedadImagenModel, Long> {
    @org.springframework.data.jpa.repository.EntityGraph(attributePaths = {"propiedad"})
    java.util.List<PropiedadImagenModel> findAll();
    @org.springframework.data.jpa.repository.EntityGraph(attributePaths = {"propiedad"})
    java.util.Optional<PropiedadImagenModel> findById(Long id);

    @org.springframework.data.jpa.repository.EntityGraph(attributePaths = {"propiedad"})
    List<PropiedadImagenModel> findByPropiedad_IdPropiedad(Long idPropiedad);
}
