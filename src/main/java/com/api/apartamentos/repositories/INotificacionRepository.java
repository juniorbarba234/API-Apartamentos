package com.api.apartamentos.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.api.apartamentos.models.NotificacionModel;

public interface INotificacionRepository extends JpaRepository<NotificacionModel, Long> {
    @org.springframework.data.jpa.repository.EntityGraph(attributePaths = {"remitente", "destinatario"})
    java.util.List<NotificacionModel> findAll();
    @org.springframework.data.jpa.repository.EntityGraph(attributePaths = {"remitente", "destinatario"})
    java.util.Optional<NotificacionModel> findById(Long id);

    @org.springframework.data.jpa.repository.EntityGraph(attributePaths = {"remitente", "destinatario"})
    List<NotificacionModel> findByDestinatario_Id(Long idCliente);
}
