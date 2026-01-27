package br.ifs.cads.api.hotel.controller;

import br.ifs.cads.api.hotel.dto.HospedeDto;
import br.ifs.cads.api.hotel.service.HospedeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hospedes")
@CrossOrigin(origins = "*")
public class HospedeController {

    @Autowired
    private HospedeService hospedeService;

    @PostMapping
    public ResponseEntity<HospedeDto> criar(@RequestBody HospedeDto dto) {
        HospedeDto criado = hospedeService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HospedeDto> obterPorId(@PathVariable Long id) {
        HospedeDto dto = hospedeService.obterPorId(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<HospedeDto> obterPorCpf(@PathVariable String cpf) {
        HospedeDto dto = hospedeService.obterPorCpf(cpf);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<HospedeDto>> listar() {
        List<HospedeDto> dtos = hospedeService.listar();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<HospedeDto>> listarAtivos() {
        List<HospedeDto> dtos = hospedeService.listarAtivos();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<HospedeDto>> buscarPorNome(@RequestParam String nome) {
        List<HospedeDto> dtos = hospedeService.buscarPorNome(nome);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/cidade/{cidadeId}")
    public ResponseEntity<List<HospedeDto>> listarPorCidade(@PathVariable Long cidadeId) {
        List<HospedeDto> dtos = hospedeService.listarPorCidade(cidadeId);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<HospedeDto>> listarPorUsuario(@PathVariable Long usuarioId) {
        List<HospedeDto> dtos = hospedeService.listarPorUsuario(usuarioId);
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HospedeDto> atualizar(@PathVariable Long id, @RequestBody HospedeDto dto) {
        HospedeDto atualizado = hospedeService.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @PatchMapping("/{id}/inativar")
    public ResponseEntity<Void> inativar(@PathVariable Long id) {
        hospedeService.inativar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{hospedeId}/associar-usuario/{usuarioId}")
    public ResponseEntity<Void> associarUsuario(@PathVariable Long hospedeId, @PathVariable Long usuarioId) {
        hospedeService.associarUsuario(hospedeId, usuarioId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{hospedeId}/desassociar-usuario")
    public ResponseEntity<Void> desassociarUsuario(@PathVariable Long hospedeId) {
        hospedeService.desassociarUsuario(hospedeId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        hospedeService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
