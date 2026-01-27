package br.ifs.cads.api.hotel.service.impl;

import br.ifs.cads.api.hotel.dto.CancelamentoDto;
import br.ifs.cads.api.hotel.entity.Cancelamento;
import br.ifs.cads.api.hotel.entity.Reserva;
import br.ifs.cads.api.hotel.enums.StatusReserva;
import br.ifs.cads.api.hotel.exception.BusinessRuleException;
import br.ifs.cads.api.hotel.exception.ResourceNotFoundException;
import br.ifs.cads.api.hotel.repository.CancelamentoRepository;
import br.ifs.cads.api.hotel.repository.ReservaRepository;
import br.ifs.cads.api.hotel.service.CancelamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CancelamentoServiceImpl implements CancelamentoService {

    @Autowired
    private CancelamentoRepository cancelamentoRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Override
    public Cancelamento save(Cancelamento cancelamento) {
        if (cancelamento.getReserva() == null || cancelamento.getReserva().getId() == null) {
            throw new BusinessRuleException("Reserva é obrigatória para o cancelamento");
        }
        if (cancelamento.getMotivo() == null || cancelamento.getMotivo().isEmpty()) {
            throw new BusinessRuleException("Motivo do cancelamento é obrigatório");
        }

        // Verificar se a reserva existe
        Reserva reserva = reservaRepository.findById(cancelamento.getReserva().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Reserva não encontrada"));

        // Verificar se a reserva já foi cancelada
        if (reserva.getStatusReserva() == StatusReserva.CANCELADA) {
            throw new BusinessRuleException("Esta reserva já foi cancelada");
        }

        // Verificar se já existe cancelamento para esta reserva
        if (cancelamentoRepository.findByReservaId(reserva.getId()).isPresent()) {
            throw new BusinessRuleException("Já existe um cancelamento registrado para esta reserva");
        }

        cancelamento.setReserva(reserva);
        cancelamento.setDataCancelamento(LocalDateTime.now());

        // Alterar status da reserva para CANCELADA
        reserva.setStatusReserva(StatusReserva.CANCELADA);
        reservaRepository.save(reserva);

        return cancelamentoRepository.save(cancelamento);
    }

    @Override
    public Optional<Cancelamento> findById(Long id) {
        return cancelamentoRepository.findById(id);
    }

    @Override
    public List<Cancelamento> findAll() {
        return cancelamentoRepository.findAll();
    }

    @Override
    public Optional<Cancelamento> findByReserva(Long reservaId) {
        return cancelamentoRepository.findByReservaId(reservaId);
    }

    @Override
    public List<Cancelamento> findByPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return cancelamentoRepository.findByDataCancelamentoBetween(dataInicio, dataFim);
    }

    @Override
    public List<Cancelamento> findByMotivo(String motivo) {
        return cancelamentoRepository.findByMotivoContainingIgnoreCase(motivo);
    }

    @Override
    public List<Cancelamento> findAtivos() {
        return cancelamentoRepository.findByAtivo(true);
    }

    @Override
    public Cancelamento update(Long id, Cancelamento cancelamentoDetails) {
        Cancelamento cancelamento = cancelamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cancelamento não encontrado"));

        if (cancelamentoDetails.getMotivo() != null && !cancelamentoDetails.getMotivo().isEmpty()) {
            cancelamento.setMotivo(cancelamentoDetails.getMotivo());
        }
        if (cancelamentoDetails.getValorMulta() != null) {
            cancelamento.setValorMulta(cancelamentoDetails.getValorMulta());
        }
        if (cancelamentoDetails.getJustificativa() != null) {
            cancelamento.setJustificativa(cancelamentoDetails.getJustificativa());
        }
        if (cancelamentoDetails.getAtivo() != null) {
            cancelamento.setAtivo(cancelamentoDetails.getAtivo());
        }

        return cancelamentoRepository.save(cancelamento);
    }

    @Override
    public void delete(Long id) {
        Cancelamento cancelamento = cancelamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cancelamento não encontrado"));
        cancelamentoRepository.delete(cancelamento);
    }

    @Override
    public CancelamentoDto convertToDto(Cancelamento cancelamento) {
        CancelamentoDto dto = new CancelamentoDto();
        dto.setId(cancelamento.getId());
        dto.setDataCancelamento(cancelamento.getDataCancelamento());
        dto.setMotivo(cancelamento.getMotivo());
        dto.setValorMulta(cancelamento.getValorMulta());
        dto.setJustificativa(cancelamento.getJustificativa());
        dto.setAtivo(cancelamento.getAtivo());

        if (cancelamento.getReserva() != null) {
            dto.setReservaId(cancelamento.getReserva().getId());
            if (cancelamento.getReserva().getHospede() != null) {
                dto.setHospedeNome(cancelamento.getReserva().getHospede().getNome());
            }
            if (cancelamento.getReserva().getQuarto() != null) {
                dto.setQuartoNumero(String.valueOf(cancelamento.getReserva().getQuarto().getNumeroQuarto()));
            }
        }

        return dto;
    }

    @Override
    public Cancelamento convertToEntity(CancelamentoDto dto) {
        Cancelamento cancelamento = new Cancelamento();
        cancelamento.setId(dto.getId());
        cancelamento.setMotivo(dto.getMotivo());
        cancelamento.setValorMulta(dto.getValorMulta());
        cancelamento.setJustificativa(dto.getJustificativa());
        cancelamento.setAtivo(dto.getAtivo() != null ? dto.getAtivo() : true);

        if (dto.getReservaId() != null) {
            Reserva reserva = new Reserva();
            reserva.setId(dto.getReservaId());
            cancelamento.setReserva(reserva);
        }

        return cancelamento;
    }
}
