package br.ifs.cads.api.hotel.service.impl;

import br.ifs.cads.api.hotel.dto.*;
import br.ifs.cads.api.hotel.entity.Hospede;
import br.ifs.cads.api.hotel.entity.Quarto;
import br.ifs.cads.api.hotel.entity.Reserva;
import br.ifs.cads.api.hotel.enums.FormaPagamento;
import br.ifs.cads.api.hotel.enums.StatusReserva;
import br.ifs.cads.api.hotel.exception.BusinessRuleException;
import br.ifs.cads.api.hotel.exception.ResourceNotFoundException;
import br.ifs.cads.api.hotel.entity.Cancelamento;
import br.ifs.cads.api.hotel.repository.HospedeRepository;
import br.ifs.cads.api.hotel.repository.QuartoRepository;
import br.ifs.cads.api.hotel.repository.ReservaRepository;
import br.ifs.cads.api.hotel.repository.CancelamentoRepository;
import br.ifs.cads.api.hotel.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservaServiceImpl implements ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private HospedeRepository hospedeRepository;

    @Autowired
    private QuartoRepository quartoRepository;

    @Autowired
    private CancelamentoRepository cancelamentoRepository;

    @Override
    public Reserva save(Reserva reserva) {
        if (reserva.getHospede() == null || reserva.getHospede().getId() == null) {
            throw new BusinessRuleException("Hóspede é obrigatório para a reserva");
        }
        if (reserva.getQuarto() == null || reserva.getQuarto().getId() == null) {
            throw new BusinessRuleException("Quarto é obrigatório para a reserva");
        }
        if (reserva.getDataCheckIn() == null) {
            throw new BusinessRuleException("Data de check-in é obrigatória");
        }
        if (reserva.getDataCheckOut() == null) {
            throw new BusinessRuleException("Data de check-out é obrigatória");
        }
        if (reserva.getDataCheckIn().isAfter(reserva.getDataCheckOut())) {
            throw new BusinessRuleException("Data de check-in não pode ser após a data de check-out");
        }
        if (reserva.getDataCheckIn().equals(reserva.getDataCheckOut())) {
            throw new BusinessRuleException("Data de check-in e check-out não podem ser iguais");
        }
        if (reserva.getValorTotal() == null || reserva.getValorTotal() <= 0) {
            throw new BusinessRuleException("Valor total da reserva deve ser maior que zero");
        }
        if (reserva.getFormaPagamento() == null) {
            throw new BusinessRuleException("Forma de pagamento é obrigatória");
        }

        // Verificar hóspede
        Hospede hospede = hospedeRepository.findById(reserva.getHospede().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Hóspede não encontrado"));
        
        // Verificar quarto
        Quarto quarto = quartoRepository.findById(reserva.getQuarto().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Quarto não encontrado"));

        // Verificar disponibilidade do quarto
        List<Reserva> reservasConfirmadas = reservaRepository
                .findByQuartoIdAndStatusReserva(quarto.getId(), StatusReserva.CONFIRMADA);
        
        for (Reserva r : reservasConfirmadas) {
            if (!(reserva.getDataCheckOut().isBefore(r.getDataCheckIn()) || 
                  reserva.getDataCheckIn().isAfter(r.getDataCheckOut()))) {
                throw new BusinessRuleException("Quarto não está disponível no período solicitado");
            }
        }

        reserva.setHospede(hospede);
        reserva.setQuarto(quarto);
        reserva.setDataReserva(LocalDateTime.now());
        return reservaRepository.save(reserva);
    }

    @Override
    public Optional<Reserva> findById(Long id) {
        return reservaRepository.findById(id);
    }

    @Override
    public List<Reserva> findAll() {
        return reservaRepository.findAll();
    }

    @Override
    public List<Reserva> findByHospede(Long hospedeId) {
        return reservaRepository.findByHospedeId(hospedeId);
    }

    @Override
    public List<Reserva> findByQuarto(Long quartoId) {
        return reservaRepository.findByQuartoId(quartoId);
    }

    @Override
    public List<Reserva> findByStatus(StatusReserva statusReserva) {
        return reservaRepository.findByStatusReserva(statusReserva);
    }

    @Override
    public List<Reserva> findByPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        return reservaRepository.findByDataCheckInBetween(dataInicio, dataFim);
    }

    @Override
    public List<Reserva> findAtivas() {
        return reservaRepository.findByAtivo(true);
    }

    @Override
    public Reserva update(Long id, Reserva reservaDetails) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva não encontrada"));

        if (reservaDetails.getObservacoes() != null) {
            reserva.setObservacoes(reservaDetails.getObservacoes());
        }
        if (reservaDetails.getValorTotal() != null && reservaDetails.getValorTotal() > 0) {
            reserva.setValorTotal(reservaDetails.getValorTotal());
        }
        if (reservaDetails.getAtivo() != null) {
            reserva.setAtivo(reservaDetails.getAtivo());
        }

        return reservaRepository.save(reserva);
    }

    @Override
    public void delete(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva não encontrada"));
        reservaRepository.delete(reserva);
    }

    @Override
    public void alterarStatus(Long id, StatusReserva novoStatus) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva não encontrada"));
        reserva.setStatusReserva(novoStatus);
        reservaRepository.save(reserva);
    }

    @Override
    public ReservaDto convertToDto(Reserva reserva) {
        ReservaDto dto = new ReservaDto();
        dto.setId(reserva.getId());
        dto.setDataReserva(reserva.getDataReserva());
        dto.setDataCheckIn(reserva.getDataCheckIn());
        dto.setDataCheckOut(reserva.getDataCheckOut());
        dto.setStatusReserva(reserva.getStatusReserva().toString());
        dto.setFormaPagamento(reserva.getFormaPagamento() != null ? reserva.getFormaPagamento().toString() : null);
        dto.setValorTotal(reserva.getValorTotal());
        dto.setObservacoes(reserva.getObservacoes());
        dto.setAtivo(reserva.getAtivo());
        
        if (reserva.getHospede() != null) {
            dto.setHospedeId(reserva.getHospede().getId());
            dto.setHospedeNome(reserva.getHospede().getNome());
        }
        
        if (reserva.getQuarto() != null) {
            dto.setQuartoId(reserva.getQuarto().getId());
            dto.setQuartoNumero(String.valueOf(reserva.getQuarto().getNumeroQuarto()));
            if (reserva.getQuarto().getCategoria() != null) {
                dto.setCategoriaNome(reserva.getQuarto().getCategoria().getNome());
            }
        }
        
        return dto;
    }

    @Override
    public Reserva convertToEntity(ReservaDto dto) {
        Reserva reserva = new Reserva();
        reserva.setId(dto.getId());
        reserva.setDataCheckIn(dto.getDataCheckIn());
        reserva.setDataCheckOut(dto.getDataCheckOut());
        reserva.setValorTotal(dto.getValorTotal());
        reserva.setObservacoes(dto.getObservacoes());
        reserva.setAtivo(dto.getAtivo() != null ? dto.getAtivo() : true);
        
        if (dto.getStatusReserva() != null) {
            try {
                reserva.setStatusReserva(StatusReserva.valueOf(dto.getStatusReserva()));
            } catch (IllegalArgumentException e) {
                reserva.setStatusReserva(StatusReserva.PENDENTE);
            }
        }

        if (dto.getFormaPagamento() != null) {
            try {
                reserva.setFormaPagamento(FormaPagamento.valueOf(dto.getFormaPagamento().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new BusinessRuleException("Forma de pagamento inválida: " + dto.getFormaPagamento());
            }
        }
        
        if (dto.getHospedeId() != null) {
            Hospede hospede = new Hospede();
            hospede.setId(dto.getHospedeId());
            reserva.setHospede(hospede);
        }
        
        if (dto.getQuartoId() != null) {
            Quarto quarto = new Quarto();
            quarto.setId(dto.getQuartoId());
            reserva.setQuarto(quarto);
        }
        
        return reserva;
    }

    @Override
    public List<RelatorioReservaPeriodoDto> relatorioReservasPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        List<Reserva> reservas = reservaRepository.findByDataCheckInBetween(dataInicio, dataFim);
        
        return reservas.stream()
                .map(reserva -> {
                    RelatorioReservaPeriodoDto dto = new RelatorioReservaPeriodoDto();
                    dto.setIdReserva(reserva.getId());
                    dto.setNomeHospede(reserva.getHospede() != null ? reserva.getHospede().getNome() : "N/A");
                    dto.setCategoriaQuarto(reserva.getQuarto() != null && reserva.getQuarto().getCategoria() != null 
                            ? reserva.getQuarto().getCategoria().getNome() : "N/A");
                    dto.setDataInicio(reserva.getDataCheckIn());
                    dto.setDataFim(reserva.getDataCheckOut());
                    dto.setStatusReserva(reserva.getStatusReserva().toString());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Page<RelatorioReservaFormaPagamentoDto> relatorioReservasPorFormaPagamento(FormaPagamento formaPagamento,
                                                                                      LocalDate dataInicio,
                                                                                      LocalDate dataFim,
                                                                                      Pageable pageable) {
        // Buscar reservas com CHECKOUT no período
        List<Reserva> reservas = reservaRepository
                .findByStatusReservaAndDataCheckOutBetween(StatusReserva.CHECKOUT, dataInicio, dataFim);

        // Filtrar por forma de pagamento se informado
        if (formaPagamento != null) {
            reservas = reservas.stream()
                    .filter(r -> formaPagamento.equals(r.getFormaPagamento()))
                    .collect(Collectors.toList());
        }

        // Agrupar por forma de pagamento e somar valores
        Map<FormaPagamento, List<Reserva>> agrupado = reservas.stream()
                .filter(r -> r.getFormaPagamento() != null)
                .collect(Collectors.groupingBy(Reserva::getFormaPagamento));

        List<RelatorioReservaFormaPagamentoDto> dtos = agrupado.entrySet().stream()
                .map(entry -> {
                    FormaPagamento fp = entry.getKey();
                    List<Reserva> rs = entry.getValue();
                    long quantidade = rs.size();
                    double total = rs.stream()
                            .map(Reserva::getValorTotal)
                            .filter(v -> v != null)
                            .mapToDouble(Double::doubleValue)
                            .sum();
                    return new RelatorioReservaFormaPagamentoDto(fp, quantidade, total);
                })
                // Ordenar por valor total recebido (desc)
                .sorted(Comparator.comparing(RelatorioReservaFormaPagamentoDto::getValorTotalRecebido).reversed())
                .collect(Collectors.toList());

        // Paginação manual
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), dtos.size());
        List<RelatorioReservaFormaPagamentoDto> pagina = start <= end ? dtos.subList(start, end) : List.of();

        return new PageImpl<>(pagina, pageable, dtos.size());
    }

    @Override
    public RelatorioFaturamentoDto relatorioFaturamento(LocalDate dataInicio, LocalDate dataFim) {
        // Faturamento considera reservas no período (data de check-out) e ignora canceladas
        List<Reserva> reservasPeriodo = reservaRepository.findByDataCheckOutBetween(dataInicio, dataFim)
                .stream()
                .filter(r -> r.getStatusReserva() != StatusReserva.CANCELADO)
                .collect(Collectors.toList());

        double totalBrutoReservas = reservasPeriodo.stream()
                .map(Reserva::getValorTotal)
                .filter(v -> v != null)
                .mapToDouble(Double::doubleValue)
                .sum();

        // Desconto de 20% para diárias de segunda a quinta
        double totalDescontos = reservasPeriodo.stream()
                .mapToDouble(reserva -> calcularDescontoReserva(reserva))
                .sum();

        double totalLiquidoReservas = totalBrutoReservas - totalDescontos;

        // Multas de cancelamento entram como receita - cálculo no CancelamentoService já existente
        // Aqui apenas poderíamos somar se houvesse integração direta; para manter a separação,
        // consideramos que o faturamento de multas é tratado em relatório específico (UC-03).
        // Caso queira incluir multas aqui, seria necessário injetar CancelamentoRepository/Service
        // e somar as multas do período.

        double totalBruto = totalBrutoReservas;
        double totalLiquido = totalLiquidoReservas;

        return new RelatorioFaturamentoDto(totalBruto, totalDescontos, totalLiquido);
    }

        // UC-07: Relatório de Histórico Completo do Hóspede
        @Override
        public Page<RelatorioHistoricoHospedeDto> relatorioHistoricoHospede(Long hospedeId, Pageable pageable) {
        if (!hospedeRepository.existsById(hospedeId)) {
            throw new ResourceNotFoundException("Hóspede com ID " + hospedeId + " não encontrado");
        }

        List<Reserva> reservas = reservaRepository.findByHospedeId(hospedeId);

        List<RelatorioHistoricoHospedeDto> dtos = reservas.stream()
            .sorted(Comparator.comparing(Reserva::getDataCheckIn).reversed())
            .map(reserva -> {
                RelatorioHistoricoHospedeDto dto = new RelatorioHistoricoHospedeDto();
                dto.setIdReserva(reserva.getId());
                dto.setDataCheckIn(reserva.getDataCheckIn());
                dto.setDataCheckOut(reserva.getDataCheckOut());
                dto.setCategoriaQuarto(reserva.getQuarto() != null && reserva.getQuarto().getCategoria() != null
                    ? reserva.getQuarto().getCategoria().getNome() : "N/A");
                dto.setStatusReserva(reserva.getStatusReserva() != null ? reserva.getStatusReserva().toString() : null);
                dto.setValorPago(reserva.getValorTotal());
                return dto;
            })
            .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), dtos.size());
        List<RelatorioHistoricoHospedeDto> pagina = start <= end ? dtos.subList(start, end) : List.of();

        return new PageImpl<>(pagina, pageable, dtos.size());
        }

        // UC-08: Relatório de Utilização de Categorias
        @Override
        public List<RelatorioUtilizacaoCategoriaDto> relatorioUtilizacaoCategorias(LocalDate dataInicio, LocalDate dataFim) {
        List<Reserva> reservas = reservaRepository
            .findByStatusReservaAndDataCheckOutBetween(StatusReserva.CHECKOUT, dataInicio, dataFim)
            .stream()
            .filter(reserva -> reserva.getQuarto() != null
                && reserva.getQuarto().getCategoria() != null)
            .collect(Collectors.toList());

        long totalReservasGeral = reservas.size();

        Map<String, List<Reserva>> agrupadoPorCategoria = reservas.stream()
            .collect(Collectors.groupingBy(r -> r.getQuarto().getCategoria().getNome()));

        return agrupadoPorCategoria.entrySet().stream()
            .map(entry -> {
                String categoria = entry.getKey();
                long totalReservas = entry.getValue().size();
                double taxaOcupacao = totalReservasGeral > 0
                    ? (totalReservas * 100.0) / totalReservasGeral
                    : 0.0;
                return new RelatorioUtilizacaoCategoriaDto(categoria, totalReservas, taxaOcupacao);
            })
            .sorted(Comparator.comparing(RelatorioUtilizacaoCategoriaDto::getTotalReservas).reversed())
            .collect(Collectors.toList());
        }

        // UC-09: Relatório Gerencial Completo
        @Override
        public RelatorioGerencialCompletoDto relatorioGerencialCompleto(LocalDate dataInicio,
                                        LocalDate dataFim,
                                        Pageable pageableCategorias) {
        // Total de reservas no período (considerando data de check-in)
        List<Reserva> reservasPeriodoCheckIn = reservaRepository.findByDataCheckInBetween(dataInicio, dataFim);
        long totalReservas = reservasPeriodoCheckIn.size();

        // Total de cancelamentos no período (data de cancelamento)
        LocalDateTime inicioPeriodo = dataInicio.atStartOfDay();
        LocalDateTime fimPeriodo = dataFim.plusDays(1).atStartOfDay().minusNanos(1);
        List<Cancelamento> cancelamentosPeriodo = cancelamentoRepository
            .findByDataCancelamentoBetween(inicioPeriodo, fimPeriodo);
        long totalCancelamentos = cancelamentosPeriodo.size();

        // Faturamento reutilizando regra de negócio existente
        RelatorioFaturamentoDto faturamento = relatorioFaturamento(dataInicio, dataFim);

        // Ocupação média no período
        List<Reserva> reservasCheckoutPeriodo = reservaRepository
            .findByStatusReservaAndDataCheckOutBetween(StatusReserva.CHECKOUT, dataInicio, dataFim);

        long diasPeriodo = ChronoUnit.DAYS.between(dataInicio, dataFim) + 1;
        double ocupacaoMediaPercentual = 0.0;

        List<Quarto> quartosAtivos = quartoRepository.findByAtivo(true);
        long quantidadeQuartosAtivos = quartosAtivos.size();

        if (diasPeriodo > 0 && quantidadeQuartosAtivos > 0) {
            long capacidadeTotalDiarias = diasPeriodo * quantidadeQuartosAtivos;

            long diariasReservadas = reservasCheckoutPeriodo.stream()
                .mapToLong(reserva -> calcularDiariasNoPeriodo(reserva, dataInicio, dataFim))
                .sum();

            ocupacaoMediaPercentual = capacidadeTotalDiarias > 0
                ? (diariasReservadas * 100.0) / capacidadeTotalDiarias
                : 0.0;
        }

        // Categorias mais rentáveis (paginadas)
        Map<String, List<Reserva>> reservasPorCategoria = reservasCheckoutPeriodo.stream()
            .filter(reserva -> reserva.getQuarto() != null
                && reserva.getQuarto().getCategoria() != null)
            .collect(Collectors.groupingBy(r -> r.getQuarto().getCategoria().getNome()));

        List<RelatorioCategoriaRentabilidadeDto> categorias = reservasPorCategoria.entrySet().stream()
            .map(entry -> {
                String categoria = entry.getKey();
                List<Reserva> rs = entry.getValue();
                long totalReservasCategoria = rs.size();
                double totalFaturado = rs.stream()
                    .map(Reserva::getValorTotal)
                    .filter(v -> v != null)
                    .mapToDouble(Double::doubleValue)
                    .sum();
                return new RelatorioCategoriaRentabilidadeDto(categoria, totalReservasCategoria, totalFaturado);
            })
            .sorted(Comparator.comparing(RelatorioCategoriaRentabilidadeDto::getTotalFaturado).reversed())
            .collect(Collectors.toList());

        int start = (int) pageableCategorias.getOffset();
        int end = Math.min(start + pageableCategorias.getPageSize(), categorias.size());
        List<RelatorioCategoriaRentabilidadeDto> paginaCategorias = start <= end
            ? categorias.subList(start, end)
            : List.of();

        Page<RelatorioCategoriaRentabilidadeDto> pageCategorias = new PageImpl<>(
            paginaCategorias,
            pageableCategorias,
            categorias.size());

        RelatorioGerencialCompletoDto dto = new RelatorioGerencialCompletoDto();
        dto.setTotalReservas(totalReservas);
        dto.setTotalCancelamentos(totalCancelamentos);
        dto.setFaturamento(faturamento);
        dto.setOcupacaoMediaPercentual(ocupacaoMediaPercentual);
        dto.setCategoriasMaisRentaveis(pageCategorias);

        return dto;
        }

    private double calcularDescontoReserva(Reserva reserva) {
        if (reserva.getDataCheckIn() == null || reserva.getDataCheckOut() == null || reserva.getValorTotal() == null) {
            return 0.0;
        }

        long dias = ChronoUnit.DAYS.between(reserva.getDataCheckIn(), reserva.getDataCheckOut());
        if (dias <= 0) {
            return 0.0;
        }

        double valorDiaria = reserva.getValorTotal() / dias;
        double descontoTotal = 0.0;

        LocalDate data = reserva.getDataCheckIn();
        for (int i = 0; i < dias; i++) {
            switch (data.getDayOfWeek()) {
                case MONDAY:
                case TUESDAY:
                case WEDNESDAY:
                case THURSDAY:
                    descontoTotal += valorDiaria * 0.20;
                    break;
                default:
                    break;
            }
            data = data.plusDays(1);
        }

        return descontoTotal;
    }

    private long calcularDiariasNoPeriodo(Reserva reserva, LocalDate dataInicio, LocalDate dataFim) {
        if (reserva.getDataCheckIn() == null || reserva.getDataCheckOut() == null) {
            return 0L;
        }

        LocalDate inicioReserva = reserva.getDataCheckIn();
        LocalDate fimReserva = reserva.getDataCheckOut();

        LocalDate inicio = inicioReserva.isAfter(dataInicio) ? inicioReserva : dataInicio;
        LocalDate fim = fimReserva.isBefore(dataFim) ? fimReserva : dataFim;

        long dias = ChronoUnit.DAYS.between(inicio, fim);
        return Math.max(dias, 0L);
    }
}
