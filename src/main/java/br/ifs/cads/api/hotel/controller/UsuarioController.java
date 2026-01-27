package br.ifs.cads.api.hotel.controller;

import br.ifs.cads.api.hotel.dto.UsuarioDto;
import br.ifs.cads.api.hotel.enums.PapelUsuario;
import br.ifs.cads.api.hotel.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioDto> criar(@RequestBody UsuarioDto dto) {
        UsuarioDto criado = usuarioService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> obterPorId(@PathVariable Long id) {
        UsuarioDto dto = usuarioService.obterPorId(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioDto> obterPorEmail(@PathVariable String email) {
        UsuarioDto dto = usuarioService.obterPorEmail(email);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDto>> listar() {
        List<UsuarioDto> dtos = usuarioService.listar();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<UsuarioDto>> listarAtivos() {
        List<UsuarioDto> dtos = usuarioService.listarAtivos();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/papel/{papel}")
    public ResponseEntity<List<UsuarioDto>> listarPorPapel(@PathVariable String papel) {
        PapelUsuario papelUsuario = PapelUsuario.valueOf(papel.toUpperCase());
        List<UsuarioDto> dtos = usuarioService.listarPorPapel(papelUsuario);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/papel/{papel}/ativos")
    public ResponseEntity<List<UsuarioDto>> listarPorPapelAtivos(@PathVariable String papel) {
        PapelUsuario papelUsuario = PapelUsuario.valueOf(papel.toUpperCase());
        List<UsuarioDto> dtos = usuarioService.listarPorPapelAtivos(papelUsuario);
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDto> atualizar(@PathVariable Long id, @RequestBody UsuarioDto dto) {
        UsuarioDto atualizado = usuarioService.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @PatchMapping("/{id}/inativar")
    public ResponseEntity<Void> inativar(@PathVariable Long id) {
        usuarioService.inativar(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/verificar-email/{email}")
    public ResponseEntity<Boolean> verificarEmailExistente(@PathVariable String email) {
        boolean existe = usuarioService.verificarEmailExistente(email);
        return ResponseEntity.ok(existe);
    }
}
