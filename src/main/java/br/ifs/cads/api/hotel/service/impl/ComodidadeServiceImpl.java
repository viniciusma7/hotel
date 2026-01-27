package br.ifs.cads.api.hotel.service.impl;

import br.ifs.cads.api.hotel.dto.ComodidadeDto;
import br.ifs.cads.api.hotel.entity.Comodidade;
import br.ifs.cads.api.hotel.exception.ResourceNotFoundException;
import br.ifs.cads.api.hotel.repository.ComodidadeRepository;
import br.ifs.cads.api.hotel.service.ComodidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ComodidadeServiceImpl implements ComodidadeService {

    @Autowired
    private ComodidadeRepository comodidadeRepository;

    @Override
    public ComodidadeDto criar(ComodidadeDto dto) {
        Comodidade comodidade = new Comodidade();
        comodidade.setNome(dto.getNome());
        comodidade.setDescricao(dto.getDescricao());
        comodidade.setAtivo(true);

        Comodidade salva = comodidadeRepository.save(comodidade);
        return converterParaDto(salva);
    }

    @Override
    @Transactional(readOnly = true)
    public ComodidadeDto obterPorId(Long id) {
        Comodidade comodidade = comodidadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comodidade com ID " + id + " não encontrada"));
        return converterParaDto(comodidade);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComodidadeDto> listar() {
        return comodidadeRepository.findAll()
                .stream()
                .map(this::converterParaDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComodidadeDto> listarAtivas() {
        return comodidadeRepository.findByAtivo(true)
                .stream()
                .map(this::converterParaDto)
                .collect(Collectors.toList());
    }

    @Override
    public ComodidadeDto atualizar(Long id, ComodidadeDto dto) {
        Comodidade comodidade = comodidadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comodidade com ID " + id + " não encontrada"));

        if (dto.getNome() != null) {
            comodidade.setNome(dto.getNome());
        }
        if (dto.getDescricao() != null) {
            comodidade.setDescricao(dto.getDescricao());
        }

        Comodidade atualizada = comodidadeRepository.save(comodidade);
        return converterParaDto(atualizada);
    }

    @Override
    public void inativar(Long id) {
        Comodidade comodidade = comodidadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comodidade com ID " + id + " não encontrada"));
        comodidade.setAtivo(false);
        comodidadeRepository.save(comodidade);
    }

    @Override
    public void deletar(Long id) {
        if (!comodidadeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Comodidade com ID " + id + " não encontrada");
        }
        comodidadeRepository.deleteById(id);
    }

    private ComodidadeDto converterParaDto(Comodidade comodidade) {
        return new ComodidadeDto(
                comodidade.getId(),
                comodidade.getNome(),
                comodidade.getDescricao(),
                comodidade.getAtivo()
        );
    }
}
