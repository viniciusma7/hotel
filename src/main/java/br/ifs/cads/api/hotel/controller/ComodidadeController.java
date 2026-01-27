package br.ifs.cads.api.hotel.controller;

import br.ifs.cads.api.hotel.dto.ComodidadeDto;
import br.ifs.cads.api.hotel.service.ComodidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comodidades")
@CrossOrigin(origins = "*")
public class ComodidadeController {

    @Autowired
    private ComodidadeService comodidadeService;

    @PostMapping
    public ResponseEntity<ComodidadeDto> criar(@RequestBody ComodidadeDto dto) {
        ComodidadeDto criada = comodidadeService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComodidadeDto> obterPorId(@PathVariable Long id) {
        ComodidadeDto dto = comodidadeService.obterPorId(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<ComodidadeDto>> listar() {
        List<ComodidadeDto> dtos = comodidadeService.listar();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/ativas")
    public ResponseEntity<List<ComodidadeDto>> listarAtivas() {
        List<ComodidadeDto> dtos = comodidadeService.listarAtivas();
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComodidadeDto> atualizar(@PathVariable Long id, @RequestBody ComodidadeDto dto) {
        ComodidadeDto atualizada = comodidadeService.atualizar(id, dto);
        return ResponseEntity.ok(atualizada);
    }

    @PatchMapping("/{id}/inativar")
    public ResponseEntity<Void> inativar(@PathVariable Long id) {
        comodidadeService.inativar(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        comodidadeService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
