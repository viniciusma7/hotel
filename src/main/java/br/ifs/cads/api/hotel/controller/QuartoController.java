package br.ifs.cads.api.hotel.controller;

import br.ifs.cads.api.hotel.dto.QuartoDto;
import br.ifs.cads.api.hotel.enums.StatusQuarto;
import br.ifs.cads.api.hotel.service.QuartoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quartos")
@CrossOrigin(origins = "*")
public class QuartoController {

    @Autowired
    private QuartoService quartoService;

    @PostMapping
    public ResponseEntity<QuartoDto> criar(@RequestBody QuartoDto dto) {
        QuartoDto criado = quartoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuartoDto> obterPorId(@PathVariable Long id) {
        QuartoDto dto = quartoService.obterPorId(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<QuartoDto>> listar() {
        List<QuartoDto> dtos = quartoService.listar();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<QuartoDto>> listarAtivos() {
        List<QuartoDto> dtos = quartoService.listarAtivos();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<QuartoDto>> listarPorStatus(@PathVariable String status) {
        StatusQuarto statusQuarto = StatusQuarto.valueOf(status.toUpperCase());
        List<QuartoDto> dtos = quartoService.listarPorStatus(statusQuarto);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<QuartoDto>> listarPorCategoria(@PathVariable Long categoriaId) {
        List<QuartoDto> dtos = quartoService.listarPorCategoria(categoriaId);
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuartoDto> atualizar(@PathVariable Long id, @RequestBody QuartoDto dto) {
        QuartoDto atualizado = quartoService.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<QuartoDto> alterarStatus(@PathVariable Long id, @RequestParam String status) {
        StatusQuarto novoStatus = StatusQuarto.valueOf(status.toUpperCase());
        QuartoDto atualizado = quartoService.alterarStatus(id, novoStatus);
        return ResponseEntity.ok(atualizado);
    }

    @PatchMapping("/{id}/inativar")
    public ResponseEntity<Void> inativar(@PathVariable Long id) {
        quartoService.inativar(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        quartoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
