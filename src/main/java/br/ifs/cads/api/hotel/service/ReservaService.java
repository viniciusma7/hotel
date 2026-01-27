package br.ifs.cads.api.hotel.service;

import br.ifs.cads.api.hotel.dto.RelatorioReservaPeriodoDto;
import br.ifs.cads.api.hotel.dto.ReservaDto;
import br.ifs.cads.api.hotel.entity.Reserva;
import br.ifs.cads.api.hotel.enums.StatusReserva;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservaService {
    Reserva save(Reserva reserva);
    Optional<Reserva> findById(Long id);
    List<Reserva> findAll();
    List<Reserva> findByHospede(Long hospedeId);
    List<Reserva> findByQuarto(Long quartoId);
    List<Reserva> findByStatus(StatusReserva statusReserva);
    List<Reserva> findByPeriodo(LocalDate dataInicio, LocalDate dataFim);
    List<Reserva> findAtivas();
    Reserva update(Long id, Reserva reservaDetails);
    void delete(Long id);
    void alterarStatus(Long id, StatusReserva novoStatus);
    ReservaDto convertToDto(Reserva reserva);
    Reserva convertToEntity(ReservaDto dto);
    
    // UC-01: Relatório de Reservas por Período
    List<RelatorioReservaPeriodoDto> relatorioReservasPorPeriodo(LocalDate dataInicio, LocalDate dataFim);
}
