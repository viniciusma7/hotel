package br.ifs.cads.api.hotel.service;

import br.ifs.cads.api.hotel.dto.*;
import br.ifs.cads.api.hotel.entity.Reserva;
import br.ifs.cads.api.hotel.enums.FormaPagamento;
import br.ifs.cads.api.hotel.enums.StatusReserva;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    // UC-05: Relatório de Reservas por Forma de Pagamento
    Page<RelatorioReservaFormaPagamentoDto> relatorioReservasPorFormaPagamento(FormaPagamento formaPagamento,
                                                                               LocalDate dataInicio,
                                                                               LocalDate dataFim,
                                                                               Pageable pageable);

    // UC-06: Relatório Financeiro de Faturamento
    RelatorioFaturamentoDto relatorioFaturamento(LocalDate dataInicio, LocalDate dataFim);

    // UC-07: Relatório de Histórico Completo do Hóspede
    Page<RelatorioHistoricoHospedeDto> relatorioHistoricoHospede(Long hospedeId, Pageable pageable);

    // UC-08: Relatório de Utilização de Categorias
    List<RelatorioUtilizacaoCategoriaDto> relatorioUtilizacaoCategorias(LocalDate dataInicio, LocalDate dataFim);

    // UC-09: Relatório Gerencial Completo
    RelatorioGerencialCompletoDto relatorioGerencialCompleto(LocalDate dataInicio,
                                                             LocalDate dataFim,
                                                             Pageable pageableCategorias);
}
