package br.ifs.cads.api.hotel.controller;

import br.ifs.cads.api.hotel.dto.RelatorioCancelamentoMultaDto;
import br.ifs.cads.api.hotel.dto.RelatorioOcupacaoQuartoDto;
import br.ifs.cads.api.hotel.dto.RelatorioReservaPeriodoDto;
import br.ifs.cads.api.hotel.enums.StatusQuarto;
import br.ifs.cads.api.hotel.service.CancelamentoService;
import br.ifs.cads.api.hotel.service.QuartoService;
import br.ifs.cads.api.hotel.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/relatorios")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RelatorioController {

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private QuartoService quartoService;

    @Autowired
    private CancelamentoService cancelamentoService;

    /**
     * UC-01: Relatório de Reservas por Período
     * 
     * @param dataInicio Data inicial do período
     * @param dataFim Data final do período
     * @return Lista de reservas no período (sem paginação)
     */
    @GetMapping("/reservas-periodo")
    public ResponseEntity<List<RelatorioReservaPeriodoDto>> relatorioReservasPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        
        List<RelatorioReservaPeriodoDto> relatorio = reservaService.relatorioReservasPorPeriodo(dataInicio, dataFim);
        return ResponseEntity.ok(relatorio);
    }

    /**
     * UC-02: Relatório de Ocupação de Quartos
     * 
     * @param categoriaId ID da categoria (opcional)
     * @param status Status do quarto
     * @param page Número da página (padrão: 0)
     * @param size Tamanho da página (padrão: 10)
     * @param sortBy Campo de ordenação: "bloco" ou "andar" (padrão: bloco)
     * @return Página de quartos com informações de ocupação
     */
    @GetMapping("/ocupacao-quartos")
    public ResponseEntity<Page<RelatorioOcupacaoQuartoDto>> relatorioOcupacaoQuartos(
            @RequestParam(required = false) Long categoriaId,
            @RequestParam StatusQuarto status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "bloco") String sortBy) {
        
        Sort sort = Sort.by(sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<RelatorioOcupacaoQuartoDto> relatorio = quartoService.relatorioOcupacaoQuartos(categoriaId, status, pageable);
        return ResponseEntity.ok(relatorio);
    }

    /**
     * UC-03: Relatório de Reservas Canceladas com Multa
     * 
     * @param dataInicio Data inicial do período de cancelamento
     * @param dataFim Data final do período de cancelamento
     * @param page Número da página (padrão: 0)
     * @param size Tamanho da página (padrão: 10)
     * @return Página de cancelamentos com multa, ordenados por valor decrescente
     */
    @GetMapping("/cancelamentos-multa")
    public ResponseEntity<Page<RelatorioCancelamentoMultaDto>> relatorioCancelamentosComMulta(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        
        Page<RelatorioCancelamentoMultaDto> relatorio = cancelamentoService.relatorioCancelamentosComMulta(dataInicio, dataFim, pageable);
        return ResponseEntity.ok(relatorio);
    }
}
