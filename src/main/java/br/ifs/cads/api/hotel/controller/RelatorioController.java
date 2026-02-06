package br.ifs.cads.api.hotel.controller;

import br.ifs.cads.api.hotel.dto.RelatorioCancelamentoMultaDto;
import br.ifs.cads.api.hotel.dto.RelatorioFaturamentoDto;
import br.ifs.cads.api.hotel.dto.RelatorioGerencialCompletoDto;
import br.ifs.cads.api.hotel.dto.RelatorioHistoricoHospedeDto;
import br.ifs.cads.api.hotel.dto.RelatorioHospedeAtivoDto;
import br.ifs.cads.api.hotel.dto.RelatorioOcupacaoQuartoDto;
import br.ifs.cads.api.hotel.dto.RelatorioReservaFormaPagamentoDto;
import br.ifs.cads.api.hotel.dto.RelatorioReservaPeriodoDto;
import br.ifs.cads.api.hotel.dto.RelatorioUtilizacaoCategoriaDto;
import br.ifs.cads.api.hotel.enums.FormaPagamento;
import br.ifs.cads.api.hotel.enums.StatusQuarto;
import br.ifs.cads.api.hotel.service.CancelamentoService;
import br.ifs.cads.api.hotel.service.HospedeService;
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

    @Autowired
    private HospedeService hospedeService;

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

    /**
     * UC-04: Relatório de Hóspedes Ativos
     *
     * @return Lista de hóspedes com usuário ativo, sem exposição de senha
     */
    @GetMapping("/hospedes-ativos")
    public ResponseEntity<List<RelatorioHospedeAtivoDto>> relatorioHospedesAtivos() {
        List<RelatorioHospedeAtivoDto> relatorio = hospedeService.relatorioHospedesAtivos();
        return ResponseEntity.ok(relatorio);
    }

    /**
     * UC-05: Relatório de Reservas por Forma de Pagamento
     *
     * @param formaPagamento Forma de pagamento (opcional)
     * @param dataInicio Data inicial do período
     * @param dataFim Data final do período
     * @param page Número da página (padrão: 0)
     * @param size Tamanho da página (padrão: 10)
     * @return Página com agregação por forma de pagamento, ordenada por valor total
     */
    @GetMapping("/reservas-forma-pagamento")
    public ResponseEntity<Page<RelatorioReservaFormaPagamentoDto>> relatorioReservasPorFormaPagamento(
            @RequestParam(required = false) FormaPagamento formaPagamento,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<RelatorioReservaFormaPagamentoDto> relatorio =
                reservaService.relatorioReservasPorFormaPagamento(formaPagamento, dataInicio, dataFim, pageable);
        return ResponseEntity.ok(relatorio);
    }

    /**
     * UC-06: Relatório Financeiro de Faturamento
     *
     * @param dataInicio Data inicial do período
     * @param dataFim Data final do período
     * @return Totais bruto, descontos e líquido do período
     */
    @GetMapping("/faturamento")
    public ResponseEntity<RelatorioFaturamentoDto> relatorioFaturamento(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {

        RelatorioFaturamentoDto relatorio = reservaService.relatorioFaturamento(dataInicio, dataFim);
        return ResponseEntity.ok(relatorio);
    }

    /**
     * UC-07: Relatório de Histórico Completo do Hóspede
     *
     * @param hospedeId ID do hóspede
     * @param page Número da página (padrão: 0)
     * @param size Tamanho da página (padrão: 10)
     * @return Página com histórico de reservas do hóspede, ordenadas por data (desc)
     */
    @GetMapping("/historico-hospede/{hospedeId}")
    public ResponseEntity<Page<RelatorioHistoricoHospedeDto>> relatorioHistoricoHospede(
            @PathVariable Long hospedeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<RelatorioHistoricoHospedeDto> relatorio = reservaService.relatorioHistoricoHospede(hospedeId, pageable);
        return ResponseEntity.ok(relatorio);
    }

    /**
     * UC-08: Relatório de Utilização de Categorias
     *
     * @param dataInicio Data inicial do período
     * @param dataFim Data final do período
     * @return Lista com utilização das categorias de quarto no período
     */
    @GetMapping("/utilizacao-categorias")
    public ResponseEntity<List<RelatorioUtilizacaoCategoriaDto>> relatorioUtilizacaoCategorias(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {

        List<RelatorioUtilizacaoCategoriaDto> relatorio =
                reservaService.relatorioUtilizacaoCategorias(dataInicio, dataFim);
        return ResponseEntity.ok(relatorio);
    }

    /**
     * UC-09: Relatório Gerencial Completo
     *
     * @param dataInicio Data inicial do período
     * @param dataFim Data final do período
     * @param pageCategorias Página da seção de categorias mais rentáveis (padrão: 0)
     * @param sizeCategorias Tamanho da página de categorias mais rentáveis (padrão: 10)
     * @return Relatório consolidado com indicadores gerenciais
     */
    @GetMapping("/gerencial-completo")
    public ResponseEntity<RelatorioGerencialCompletoDto> relatorioGerencialCompleto(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            @RequestParam(defaultValue = "0") int pageCategorias,
            @RequestParam(defaultValue = "10") int sizeCategorias) {

        Pageable pageableCategorias = PageRequest.of(pageCategorias, sizeCategorias);
        RelatorioGerencialCompletoDto relatorio =
                reservaService.relatorioGerencialCompleto(dataInicio, dataFim, pageableCategorias);
        return ResponseEntity.ok(relatorio);
    }
}
