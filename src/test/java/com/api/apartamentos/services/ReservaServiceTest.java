package com.api.apartamentos.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import com.api.apartamentos.models.*;
import com.api.apartamentos.repositories.*;

class ReservaServiceTest {
    @Test
    void calculaElPrecioDesdeLaUnidadGuardadaYRechazaSolapamientos() {
        var reservas = mock(IReservaRepository.class);
        var unidades = mock(IUnidadRentableRepository.class);
        var clientes = mock(IClienteRepository.class);
        var service = new ReservaService(reservas, unidades, clientes);
        var unidad = new UnidadRentableModel();
        unidad.setIdUnidad(1L);
        unidad.setPrecioNoche(new BigDecimal("500.00"));
        var cliente = new ClienteModel();
        cliente.setId(2L);
        when(unidades.findById(1L)).thenReturn(Optional.of(unidad));
        when(clientes.findById(2L)).thenReturn(Optional.of(cliente));
        when(reservas.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        var reserva = new ReservaModel();
        var referencia = new UnidadRentableModel();
        referencia.setIdUnidad(1L);
        reserva.setUnidad(referencia);
        reserva.setCliente(cliente);
        reserva.setFechaEntrada(LocalDateTime.parse("2026-11-01T15:00:00"));
        reserva.setFechaSalida(LocalDateTime.parse("2026-11-04T11:00:00"));
        reserva.setImporteTotal(BigDecimal.ONE);
        assertEquals(new BigDecimal("1500.00"), service.save(reserva).getImporteTotal());
        when(reservas.existeSolapamiento(eq(1L), isNull(), any(), any())).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> service.save(reserva));
        verify(reservas, times(1)).save(any());
    }
}
