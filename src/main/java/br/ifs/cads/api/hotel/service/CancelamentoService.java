package br.ifs.cads.api.hotel.service;

import br.ifs.cads.api.hotel.dto.CancelamentoDto;
import br.ifs.cads.api.hotel.dto.RelatorioCancelamentoMultaDto;
import br.ifs.cads.api.hotel.entity.Cancelamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CancelamentoService {
    Cancelamento save(Cancelamento cancelamento);
    Optional<Cancelamento> findById(Long id);
    List<Cancelamento> findAll();
    Optional<Cancelamento> findByReserva(Long reservaId);
    List<Cancelamento> findByPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim);
    List<Cancelamento> findByMotivo(String motivo);
    List<Cancelamento> findAtivos();
    Cancelamento update(Long id, Cancelamento cancelamentoDetails);
    void delete(Long id);
    CancelamentoDto convertToDto(Cancelamento cancelamento);
    Cancelamento convertToEntity(CancelamentoDto dto);
    
    // UC-03: Relatório de Cancelamentos com Multa
    Page<RelatorioCancelamentoMultaDto> relatorioCancelamentosComMulta(LocalDateTime dataInicio, LocalDateTime dataFim, Pageable pageable);
}
