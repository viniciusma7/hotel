package br.ifs.cads.api.hotel.controller;

import br.ifs.cads.api.hotel.dto.CategoriaQuartoDto;
import br.ifs.cads.api.hotel.service.CategoriaQuartoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias-quarto")
@CrossOrigin(origins = "*")
public class CategoriaQuartoController {

    @Autowired
    private CategoriaQuartoService categoriaQuartoService;

    @PostMapping
    public ResponseEntity<CategoriaQuartoDto> criar(@RequestBody CategoriaQuartoDto dto) {
        CategoriaQuartoDto criada = categoriaQuartoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaQuartoDto> obterPorId(@PathVariable Long id) {
        CategoriaQuartoDto dto = categoriaQuartoService.obterPorId(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<CategoriaQuartoDto>> listar() {
        List<CategoriaQuartoDto> dtos = categoriaQuartoService.listar();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/ativas")
    public ResponseEntity<List<CategoriaQuartoDto>> listarAtivas() {
        List<CategoriaQuartoDto> dtos = categoriaQuartoService.listarAtivas();
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaQuartoDto> atualizar(@PathVariable Long id, @RequestBody CategoriaQuartoDto dto) {
        CategoriaQuartoDto atualizada = categoriaQuartoService.atualizar(id, dto);
        return ResponseEntity.ok(atualizada);
    }

    @PatchMapping("/{id}/inativar")
    public ResponseEntity<Void> inativar(@PathVariable Long id) {
        categoriaQuartoService.inativar(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        categoriaQuartoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
