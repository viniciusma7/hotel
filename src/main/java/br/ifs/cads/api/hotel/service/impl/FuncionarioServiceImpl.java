package br.ifs.cads.api.hotel.service.impl;

import br.ifs.cads.api.hotel.dto.FuncionarioDto;
import br.ifs.cads.api.hotel.entity.Funcionario;
import br.ifs.cads.api.hotel.enums.Cargo;
import br.ifs.cads.api.hotel.exception.BusinessRuleException;
import br.ifs.cads.api.hotel.exception.ResourceNotFoundException;
import br.ifs.cads.api.hotel.repository.FuncionarioRepository;
import br.ifs.cads.api.hotel.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Override
    public Funcionario save(Funcionario funcionario) {
        if (funcionario.getCpf() == null || funcionario.getCpf().isEmpty()) {
            throw new BusinessRuleException("CPF do funcionário não pode estar vazio");
        }
        if (funcionarioRepository.findByCpf(funcionario.getCpf()).isPresent()) {
            throw new BusinessRuleException("CPF já cadastrado no sistema");
        }
        if (funcionario.getNome() == null || funcionario.getNome().isEmpty()) {
            throw new BusinessRuleException("Nome do funcionário não pode estar vazio");
        }
        if (funcionario.getCargo() == null) {
            throw new BusinessRuleException("Cargo do funcionário não pode estar vazio");
        }
        if (funcionario.getDataAdmissao() == null) {
            throw new BusinessRuleException("Data de admissão não pode estar vazia");
        }
        return funcionarioRepository.save(funcionario);
    }

    @Override
    public Optional<Funcionario> findById(Long id) {
        return funcionarioRepository.findById(id);
    }

    @Override
    public List<Funcionario> findAll() {
        return funcionarioRepository.findAll();
    }

    @Override
    public List<Funcionario> findAllAtivos() {
        return funcionarioRepository.findByAtivo(true);
    }

    @Override
    public Optional<Funcionario> findByCpf(String cpf) {
        return funcionarioRepository.findByCpf(cpf);
    }

    @Override
    public List<Funcionario> findByCargo(Cargo cargo) {
        return funcionarioRepository.findByCargo(cargo);
    }

    @Override
    public List<Funcionario> findByNome(String nome) {
        return funcionarioRepository.findByNomeContainingIgnoreCase(nome);
    }

    @Override
    public Funcionario update(Long id, Funcionario funcionarioDetails) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionário não encontrado"));

        if (funcionarioDetails.getNome() != null && !funcionarioDetails.getNome().isEmpty()) {
            funcionario.setNome(funcionarioDetails.getNome());
        }
        if (funcionarioDetails.getCargo() != null) {
            funcionario.setCargo(funcionarioDetails.getCargo());
        }
        if (funcionarioDetails.getDataAdmissao() != null) {
            funcionario.setDataAdmissao(funcionarioDetails.getDataAdmissao());
        }
        if (funcionarioDetails.getDataDemissao() != null) {
            funcionario.setDataDemissao(funcionarioDetails.getDataDemissao());
        }
        if (funcionarioDetails.getAtivo() != null) {
            funcionario.setAtivo(funcionarioDetails.getAtivo());
        }

        return funcionarioRepository.save(funcionario);
    }

    @Override
    public void delete(Long id) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionário não encontrado"));
        funcionarioRepository.delete(funcionario);
    }

    @Override
    public void inativar(Long id) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionário não encontrado"));
        funcionario.setAtivo(false);
        funcionarioRepository.save(funcionario);
    }

    @Override
    public FuncionarioDto convertToDto(Funcionario funcionario) {
        FuncionarioDto dto = new FuncionarioDto();
        dto.setId(funcionario.getId());
        dto.setNome(funcionario.getNome());
        dto.setCpf(funcionario.getCpf());
        dto.setCargo(funcionario.getCargo().toString());
        dto.setDataAdmissao(funcionario.getDataAdmissao());
        dto.setDataDemissao(funcionario.getDataDemissao());
        dto.setAtivo(funcionario.getAtivo());
        if (funcionario.getUsuario() != null) {
            dto.setUsuarioId(funcionario.getUsuario().getId());
            dto.setUsuarioEmail(funcionario.getUsuario().getEmail());
        }
        return dto;
    }

    @Override
    public Funcionario convertToEntity(FuncionarioDto dto) {
        Funcionario funcionario = new Funcionario();
        funcionario.setId(dto.getId());
        funcionario.setNome(dto.getNome());
        funcionario.setCpf(dto.getCpf());
        funcionario.setCargo(Cargo.valueOf(dto.getCargo()));
        funcionario.setDataAdmissao(dto.getDataAdmissao());
        funcionario.setDataDemissao(dto.getDataDemissao());
        funcionario.setAtivo(dto.getAtivo());
        return funcionario;
    }
}
