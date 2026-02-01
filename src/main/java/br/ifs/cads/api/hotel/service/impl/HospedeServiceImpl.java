package br.ifs.cads.api.hotel.service.impl;

import br.ifs.cads.api.hotel.dto.HospedeDto;
import br.ifs.cads.api.hotel.dto.RelatorioHospedeAtivoDto;
import br.ifs.cads.api.hotel.entity.Cidade;
import br.ifs.cads.api.hotel.entity.Hospede;
import br.ifs.cads.api.hotel.entity.Usuario;
import br.ifs.cads.api.hotel.exception.BusinessRuleException;
import br.ifs.cads.api.hotel.exception.ResourceNotFoundException;
import br.ifs.cads.api.hotel.repository.CidadeRepository;
import br.ifs.cads.api.hotel.repository.HospedeRepository;
import br.ifs.cads.api.hotel.repository.UsuarioRepository;
import br.ifs.cads.api.hotel.service.HospedeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class HospedeServiceImpl implements HospedeService {

    @Autowired
    private HospedeRepository hospedeRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public HospedeDto criar(HospedeDto dto) {
        // Validar CPF único
        if (hospedeRepository.findByCpf(dto.getCpf()).isPresent()) {
            throw new BusinessRuleException("Já existe um hóspede com o CPF: " + dto.getCpf());
        }

        // Validar cidade
        Cidade cidade = cidadeRepository.findById(dto.getCidadeId())
                .orElseThrow(() -> new ResourceNotFoundException("Cidade com ID " + dto.getCidadeId() + " não encontrada"));

        Hospede hospede = new Hospede();
        hospede.setNome(dto.getNome());
        hospede.setCpf(dto.getCpf());
        hospede.setDataNascimento(dto.getDataNascimento());
        hospede.setTelefone(dto.getTelefone());
        hospede.setCidade(cidade);
        hospede.setAtivo(true);

        // Se usuarioId foi fornecido, associar
        if (dto.getUsuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuário com ID " + dto.getUsuarioId() + " não encontrado"));
            hospede.setUsuario(usuario);
        }

        Hospede salvo = hospedeRepository.save(hospede);
        return converterParaDto(salvo);
    }

    @Override
    @Transactional(readOnly = true)
    public HospedeDto obterPorId(Long id) {
        Hospede hospede = hospedeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hóspede com ID " + id + " não encontrado"));
        return converterParaDto(hospede);
    }

    @Override
    @Transactional(readOnly = true)
    public HospedeDto obterPorCpf(String cpf) {
        Hospede hospede = hospedeRepository.findByCpf(cpf)
                .orElseThrow(() -> new ResourceNotFoundException("Hóspede com CPF " + cpf + " não encontrado"));
        return converterParaDto(hospede);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HospedeDto> listar() {
        return hospedeRepository.findAll()
                .stream()
                .map(this::converterParaDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<HospedeDto> listarAtivos() {
        return hospedeRepository.findByAtivo(true)
                .stream()
                .map(this::converterParaDto)
                .collect(Collectors.toList());
    }

        @Override
        @Transactional(readOnly = true)
        public List<RelatorioHospedeAtivoDto> relatorioHospedesAtivos() {
        return hospedeRepository.findByAtivo(true)
            .stream()
            .filter(h -> h.getUsuario() != null && Boolean.TRUE.equals(h.getUsuario().getAtivo()))
            .map(h -> new RelatorioHospedeAtivoDto(
                h.getNome(),
                h.getUsuario().getEmail(),
                h.getTelefone(),
                h.getCidade() != null ? h.getCidade().getNome() : null,
                h.getCidade() != null && h.getCidade().getEstado() != null ? h.getCidade().getEstado().getUf() : null
            ))
            .collect(Collectors.toList());
        }

    @Override
    @Transactional(readOnly = true)
    public List<HospedeDto> buscarPorNome(String nome) {
        return hospedeRepository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(this::converterParaDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<HospedeDto> listarPorCidade(Long cidadeId) {
        return hospedeRepository.findByCidadeId(cidadeId)
                .stream()
                .map(this::converterParaDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<HospedeDto> listarPorUsuario(Long usuarioId) {
        return hospedeRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::converterParaDto)
                .collect(Collectors.toList());
    }

    @Override
    public HospedeDto atualizar(Long id, HospedeDto dto) {
        Hospede hospede = hospedeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hóspede com ID " + id + " não encontrado"));

        // Se o CPF foi alterado, verificar unicidade
        if (dto.getCpf() != null && !dto.getCpf().equals(hospede.getCpf())) {
            if (hospedeRepository.findByCpf(dto.getCpf()).isPresent()) {
                throw new BusinessRuleException("Já existe um hóspede com o CPF: " + dto.getCpf());
            }
            hospede.setCpf(dto.getCpf());
        }

        if (dto.getNome() != null) {
            hospede.setNome(dto.getNome());
        }

        if (dto.getDataNascimento() != null) {
            hospede.setDataNascimento(dto.getDataNascimento());
        }

        if (dto.getTelefone() != null) {
            hospede.setTelefone(dto.getTelefone());
        }

        if (dto.getCidadeId() != null) {
            Cidade cidade = cidadeRepository.findById(dto.getCidadeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cidade com ID " + dto.getCidadeId() + " não encontrada"));
            hospede.setCidade(cidade);
        }

        Hospede atualizado = hospedeRepository.save(hospede);
        return converterParaDto(atualizado);
    }

    @Override
    public void inativar(Long id) {
        Hospede hospede = hospedeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hóspede com ID " + id + " não encontrado"));
        hospede.setAtivo(false);
        hospedeRepository.save(hospede);
    }

    @Override
    public void deletar(Long id) {
        if (!hospedeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Hóspede com ID " + id + " não encontrado");
        }
        hospedeRepository.deleteById(id);
    }

    @Override
    public void associarUsuario(Long hospedeId, Long usuarioId) {
        Hospede hospede = hospedeRepository.findById(hospedeId)
                .orElseThrow(() -> new ResourceNotFoundException("Hóspede com ID " + hospedeId + " não encontrado"));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário com ID " + usuarioId + " não encontrado"));

        hospede.setUsuario(usuario);
        hospedeRepository.save(hospede);
    }

    @Override
    public void desassociarUsuario(Long hospedeId) {
        Hospede hospede = hospedeRepository.findById(hospedeId)
                .orElseThrow(() -> new ResourceNotFoundException("Hóspede com ID " + hospedeId + " não encontrado"));
        hospede.setUsuario(null);
        hospedeRepository.save(hospede);
    }

    private HospedeDto converterParaDto(Hospede hospede) {
        HospedeDto dto = new HospedeDto(
                hospede.getId(),
                hospede.getNome(),
                hospede.getCpf(),
                hospede.getDataNascimento(),
                hospede.getTelefone(),
                hospede.getCidade().getId(),
                hospede.getCidade().getNome(),
                hospede.getAtivo()
        );

        if (hospede.getUsuario() != null) {
            dto.setUsuarioId(hospede.getUsuario().getId());
            dto.setUsuarioEmail(hospede.getUsuario().getEmail());
        }

        return dto;
    }
}
