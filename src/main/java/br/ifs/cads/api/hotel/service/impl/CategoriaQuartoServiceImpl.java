package br.ifs.cads.api.hotel.service.impl;

import br.ifs.cads.api.hotel.dto.CategoriaQuartoDto;
import br.ifs.cads.api.hotel.entity.CategoriaQuarto;
import br.ifs.cads.api.hotel.enums.Posicao;
import br.ifs.cads.api.hotel.exception.ResourceNotFoundException;
import br.ifs.cads.api.hotel.repository.CategoriaQuartoRepository;
import br.ifs.cads.api.hotel.service.CategoriaQuartoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoriaQuartoServiceImpl implements CategoriaQuartoService {

    @Autowired
    private CategoriaQuartoRepository categoriaQuartoRepository;

    @Override
    public CategoriaQuartoDto criar(CategoriaQuartoDto dto) {
        CategoriaQuarto categoria = new CategoriaQuarto();
        categoria.setNome(dto.getNome());
        categoria.setDescricao(dto.getDescricao());
        categoria.setValorDiaria(dto.getValorDiaria());
        categoria.setMaxHospedes(dto.getMaxHospedes());
        categoria.setPosicao(Posicao.valueOf(dto.getPosicao()));
        categoria.setAtivo(true);

        CategoriaQuarto salva = categoriaQuartoRepository.save(categoria);
        return converterParaDto(salva);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaQuartoDto obterPorId(Long id) {
        CategoriaQuarto categoria = categoriaQuartoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria de Quarto com ID " + id + " não encontrada"));
        return converterParaDto(categoria);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaQuartoDto> listar() {
        return categoriaQuartoRepository.findAll()
                .stream()
                .map(this::converterParaDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaQuartoDto> listarAtivas() {
        return categoriaQuartoRepository.findByAtivo(true)
                .stream()
                .map(this::converterParaDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoriaQuartoDto atualizar(Long id, CategoriaQuartoDto dto) {
        CategoriaQuarto categoria = categoriaQuartoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria de Quarto com ID " + id + " não encontrada"));

        if (dto.getNome() != null) {
            categoria.setNome(dto.getNome());
        }
        if (dto.getDescricao() != null) {
            categoria.setDescricao(dto.getDescricao());
        }
        if (dto.getValorDiaria() != null) {
            categoria.setValorDiaria(dto.getValorDiaria());
        }
        if (dto.getMaxHospedes() != null) {
            categoria.setMaxHospedes(dto.getMaxHospedes());
        }
        if (dto.getPosicao() != null) {
            categoria.setPosicao(Posicao.valueOf(dto.getPosicao()));
        }

        CategoriaQuarto atualizada = categoriaQuartoRepository.save(categoria);
        return converterParaDto(atualizada);
    }

    @Override
    public void inativar(Long id) {
        CategoriaQuarto categoria = categoriaQuartoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria de Quarto com ID " + id + " não encontrada"));
        categoria.setAtivo(false);
        categoriaQuartoRepository.save(categoria);
    }

    @Override
    public void deletar(Long id) {
        if (!categoriaQuartoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Categoria de Quarto com ID " + id + " não encontrada");
        }
        categoriaQuartoRepository.deleteById(id);
    }

    private CategoriaQuartoDto converterParaDto(CategoriaQuarto categoria) {
        return new CategoriaQuartoDto(
                categoria.getId(),
                categoria.getNome(),
                categoria.getDescricao(),
                categoria.getValorDiaria(),
                categoria.getMaxHospedes(),
                categoria.getPosicao().name(),
                categoria.getAtivo()
        );
    }
}
