package br.ifs.cads.api.hotel.controller;

import br.ifs.cads.api.hotel.dto.FuncionarioDto;
import br.ifs.cads.api.hotel.entity.Funcionario;
import br.ifs.cads.api.hotel.enums.Cargo;
import br.ifs.cads.api.hotel.exception.BusinessRuleException;
import br.ifs.cads.api.hotel.exception.ResourceNotFoundException;
import br.ifs.cads.api.hotel.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/funcionarios")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping
    public ResponseEntity<List<FuncionarioDto>> listarTodos() {
        List<Funcionario> funcionarios = funcionarioService.findAll();
        List<FuncionarioDto> dtos = funcionarios.stream()
                .map(funcionarioService::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<FuncionarioDto>> listarAtivos() {
        List<Funcionario> funcionarios = funcionarioService.findAllAtivos();
        List<FuncionarioDto> dtos = funcionarios.stream()
                .map(funcionarioService::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioDto> obterPorId(@PathVariable Long id) {
        return funcionarioService.findById(id)
                .map(f -> ResponseEntity.ok(funcionarioService.convertToDto(f)))
                .orElseThrow(() -> new ResourceNotFoundException("Funcionário não encontrado"));
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<FuncionarioDto> obterPorCpf(@PathVariable String cpf) {
        return funcionarioService.findByCpf(cpf)
                .map(f -> ResponseEntity.ok(funcionarioService.convertToDto(f)))
                .orElseThrow(() -> new ResourceNotFoundException("Funcionário com CPF " + cpf + " não encontrado"));
    }

    @GetMapping("/cargo/{cargo}")
    public ResponseEntity<List<FuncionarioDto>> obterPorCargo(@PathVariable String cargo) {
        try {
            Cargo cargoEnum = Cargo.valueOf(cargo.toUpperCase());
            List<Funcionario> funcionarios = funcionarioService.findByCargo(cargoEnum);
            List<FuncionarioDto> dtos = funcionarios.stream()
                    .map(funcionarioService::convertToDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (IllegalArgumentException e) {
            throw new BusinessRuleException("Cargo inválido: " + cargo);
        }
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<FuncionarioDto>> obterPorNome(@PathVariable String nome) {
        List<Funcionario> funcionarios = funcionarioService.findByNome(nome);
        List<FuncionarioDto> dtos = funcionarios.stream()
                .map(funcionarioService::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<FuncionarioDto> criar(@RequestBody FuncionarioDto dto) {
        Funcionario funcionario = funcionarioService.convertToEntity(dto);
        Funcionario salvo = funcionarioService.save(funcionario);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(funcionarioService.convertToDto(salvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioDto> atualizar(
            @PathVariable Long id,
            @RequestBody FuncionarioDto dto) {
        Funcionario funcionario = funcionarioService.convertToEntity(dto);
        Funcionario atualizado = funcionarioService.update(id, funcionario);
        return ResponseEntity.ok(funcionarioService.convertToDto(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        funcionarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/inativar")
    public ResponseEntity<FuncionarioDto> inativar(@PathVariable Long id) {
        funcionarioService.inativar(id);
        return funcionarioService.findById(id)
                .map(f -> ResponseEntity.ok(funcionarioService.convertToDto(f)))
                .orElseThrow(() -> new ResourceNotFoundException("Funcionário não encontrado"));
    }
}
