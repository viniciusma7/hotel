package br.ifs.cads.api.hotel.service;

import br.ifs.cads.api.hotel.dto.FuncionarioDto;
import br.ifs.cads.api.hotel.entity.Funcionario;
import br.ifs.cads.api.hotel.enums.Cargo;
import java.util.List;
import java.util.Optional;

public interface FuncionarioService {
    Funcionario save(Funcionario funcionario);
    Optional<Funcionario> findById(Long id);
    List<Funcionario> findAll();
    List<Funcionario> findAllAtivos();
    Optional<Funcionario> findByCpf(String cpf);
    List<Funcionario> findByCargo(Cargo cargo);
    List<Funcionario> findByNome(String nome);
    Funcionario update(Long id, Funcionario funcionarioDetails);
    void delete(Long id);
    void inativar(Long id);
    FuncionarioDto convertToDto(Funcionario funcionario);
    Funcionario convertToEntity(FuncionarioDto dto);
}
