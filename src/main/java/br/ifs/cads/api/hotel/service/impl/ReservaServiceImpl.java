package br.ifs.cads.api.hotel.service.impl;

import br.ifs.cads.api.hotel.dto.RelatorioReservaPeriodoDto;
import br.ifs.cads.api.hotel.dto.ReservaDto;
import br.ifs.cads.api.hotel.entity.Hospede;
import br.ifs.cads.api.hotel.entity.Quarto;
import br.ifs.cads.api.hotel.entity.Reserva;
import br.ifs.cads.api.hotel.enums.StatusReserva;
import br.ifs.cads.api.hotel.exception.BusinessRuleException;
import br.ifs.cads.api.hotel.exception.ResourceNotFoundException;
import br.ifs.cads.api.hotel.repository.HospedeRepository;
import br.ifs.cads.api.hotel.repository.QuartoRepository;
import br.ifs.cads.api.hotel.repository.ReservaRepository;
import br.ifs.cads.api.hotel.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
}
