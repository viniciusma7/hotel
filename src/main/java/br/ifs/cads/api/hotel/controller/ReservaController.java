package br.ifs.cads.api.hotel.controller;

import br.ifs.cads.api.hotel.dto.ReservaDto;
import br.ifs.cads.api.hotel.entity.Reserva;
import br.ifs.cads.api.hotel.enums.StatusReserva;
import br.ifs.cads.api.hotel.exception.BusinessRuleException;
import br.ifs.cads.api.hotel.exception.ResourceNotFoundException;
import br.ifs.cads.api.hotel.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservas")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @GetMapping
    public ResponseEntity<List<ReservaDto>> listarTodas() {
        List<Reserva> reservas = reservaService.findAll();
        List<ReservaDto> dtos = reservas.stream()
                .map(reservaService::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/ativas")
    public ResponseEntity<List<ReservaDto>> listarAtivas() {
        List<Reserva> reservas = reservaService.findAtivas();
        List<ReservaDto> dtos = reservas.stream()
                .map(reservaService::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaDto> obterPorId(@PathVariable Long id) {
        return reservaService.findById(id)
                .map(r -> ResponseEntity.ok(reservaService.convertToDto(r)))
                .orElseThrow(() -> new ResourceNotFoundException("Reserva não encontrada"));
    }

    @GetMapping("/hospede/{hospedeId}")
    public ResponseEntity<List<ReservaDto>> obterPorHospede(@PathVariable Long hospedeId) {
        List<Reserva> reservas = reservaService.findByHospede(hospedeId);
        List<ReservaDto> dtos = reservas.stream()
                .map(reservaService::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/quarto/{quartoId}")
    public ResponseEntity<List<ReservaDto>> obterPorQuarto(@PathVariable Long quartoId) {
        List<Reserva> reservas = reservaService.findByQuarto(quartoId);
        List<ReservaDto> dtos = reservas.stream()
                .map(reservaService::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ReservaDto>> obterPorStatus(@PathVariable String status) {
        try {
            StatusReserva statusEnum = StatusReserva.valueOf(status.toUpperCase());
            List<Reserva> reservas = reservaService.findByStatus(statusEnum);
            List<ReservaDto> dtos = reservas.stream()
                    .map(reservaService::convertToDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (IllegalArgumentException e) {
            throw new BusinessRuleException("Status inválido: " + status);
        }
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<ReservaDto>> obterPorPeriodo(
            @RequestParam LocalDate dataInicio,
            @RequestParam LocalDate dataFim) {
        List<Reserva> reservas = reservaService.findByPeriodo(dataInicio, dataFim);
        List<ReservaDto> dtos = reservas.stream()
                .map(reservaService::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<ReservaDto> criar(@RequestBody ReservaDto dto) {
        Reserva reserva = reservaService.convertToEntity(dto);
        Reserva salva = reservaService.save(reserva);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reservaService.convertToDto(salva));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservaDto> atualizar(
            @PathVariable Long id,
            @RequestBody ReservaDto dto) {
        Reserva reserva = reservaService.convertToEntity(dto);
        Reserva atualizada = reservaService.update(id, reserva);
        return ResponseEntity.ok(reservaService.convertToDto(atualizada));
    }

    @PatchMapping("/{id}/status/{status}")
    public ResponseEntity<ReservaDto> alterarStatus(
            @PathVariable Long id,
            @PathVariable String status) {
        try {
            StatusReserva statusEnum = StatusReserva.valueOf(status.toUpperCase());
            reservaService.alterarStatus(id, statusEnum);
            return reservaService.findById(id)
                    .map(r -> ResponseEntity.ok(reservaService.convertToDto(r)))
                    .orElseThrow(() -> new ResourceNotFoundException("Reserva não encontrada"));
        } catch (IllegalArgumentException e) {
            throw new BusinessRuleException("Status inválido: " + status);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        reservaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
