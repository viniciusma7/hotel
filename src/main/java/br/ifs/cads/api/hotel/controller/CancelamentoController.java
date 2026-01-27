package br.ifs.cads.api.hotel.controller;

import br.ifs.cads.api.hotel.dto.CancelamentoDto;
import br.ifs.cads.api.hotel.entity.Cancelamento;
import br.ifs.cads.api.hotel.exception.BusinessRuleException;
import br.ifs.cads.api.hotel.exception.ResourceNotFoundException;
import br.ifs.cads.api.hotel.service.CancelamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cancelamentos")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CancelamentoController {

    @Autowired
    private CancelamentoService cancelamentoService;

    @GetMapping
    public ResponseEntity<List<CancelamentoDto>> listarTodos() {
        List<Cancelamento> cancelamentos = cancelamentoService.findAll();
        List<CancelamentoDto> dtos = cancelamentos.stream()
                .map(cancelamentoService::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<CancelamentoDto>> listarAtivos() {
        List<Cancelamento> cancelamentos = cancelamentoService.findAtivos();
        List<CancelamentoDto> dtos = cancelamentos.stream()
                .map(cancelamentoService::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CancelamentoDto> obterPorId(@PathVariable Long id) {
        return cancelamentoService.findById(id)
                .map(c -> ResponseEntity.ok(cancelamentoService.convertToDto(c)))
                .orElseThrow(() -> new ResourceNotFoundException("Cancelamento não encontrado"));
    }

    @GetMapping("/reserva/{reservaId}")
    public ResponseEntity<CancelamentoDto> obterPorReserva(@PathVariable Long reservaId) {
        return cancelamentoService.findByReserva(reservaId)
                .map(c -> ResponseEntity.ok(cancelamentoService.convertToDto(c)))
                .orElseThrow(() -> new ResourceNotFoundException("Cancelamento não encontrado para esta reserva"));
    }

    @GetMapping("/motivo/{motivo}")
    public ResponseEntity<List<CancelamentoDto>> obterPorMotivo(@PathVariable String motivo) {
        List<Cancelamento> cancelamentos = cancelamentoService.findByMotivo(motivo);
        List<CancelamentoDto> dtos = cancelamentos.stream()
                .map(cancelamentoService::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<CancelamentoDto>> obterPorPeriodo(
            @RequestParam LocalDateTime dataInicio,
            @RequestParam LocalDateTime dataFim) {
        List<Cancelamento> cancelamentos = cancelamentoService.findByPeriodo(dataInicio, dataFim);
        List<CancelamentoDto> dtos = cancelamentos.stream()
                .map(cancelamentoService::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<CancelamentoDto> criar(@RequestBody CancelamentoDto dto) {
        Cancelamento cancelamento = cancelamentoService.convertToEntity(dto);
        Cancelamento salvo = cancelamentoService.save(cancelamento);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cancelamentoService.convertToDto(salvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CancelamentoDto> atualizar(
            @PathVariable Long id,
            @RequestBody CancelamentoDto dto) {
        Cancelamento cancelamento = cancelamentoService.convertToEntity(dto);
        Cancelamento atualizado = cancelamentoService.update(id, cancelamento);
        return ResponseEntity.ok(cancelamentoService.convertToDto(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        cancelamentoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
