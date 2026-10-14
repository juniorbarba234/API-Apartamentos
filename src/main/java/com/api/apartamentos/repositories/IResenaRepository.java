package com.api.apartamentos.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.api.apartamentos.models.ResenaModel;

public interface IResenaRepository extends JpaRepository<ResenaModel, Long> {
    @org.springframework.data.jpa.repository.EntityGraph(attributePaths = {"reserva", "cliente"})
    java.util.List<ResenaModel> findAll();
    @org.springframework.data.jpa.repository.EntityGraph(attributePaths = {"reserva", "cliente"})
    java.util.Optional<ResenaModel> findById(Long id);

    @org.springframework.data.jpa.repository.EntityGraph(attributePaths = {"reserva", "cliente"})
    List<ResenaModel> findByReserva_IdReservacion(Long idReservacion);
}
