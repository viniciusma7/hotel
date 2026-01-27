package br.ifs.cads.api.hotel.service.impl;

import br.ifs.cads.api.hotel.dto.QuartoDto;
import br.ifs.cads.api.hotel.dto.RelatorioOcupacaoQuartoDto;
import br.ifs.cads.api.hotel.entity.CategoriaQuarto;
import br.ifs.cads.api.hotel.entity.Quarto;
import br.ifs.cads.api.hotel.enums.StatusQuarto;
import br.ifs.cads.api.hotel.exception.BusinessRuleException;
import br.ifs.cads.api.hotel.exception.ResourceNotFoundException;
import br.ifs.cads.api.hotel.repository.CategoriaQuartoRepository;
import br.ifs.cads.api.hotel.repository.QuartoRepository;
import br.ifs.cads.api.hotel.service.QuartoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class QuartoServiceImpl implements QuartoService {

    @Autowired
    private QuartoRepository quartoRepository;

    @Autowired
    private CategoriaQuartoRepository categoriaQuartoRepository;

    @Override
    public QuartoDto criar(QuartoDto dto) {
        // Validar se a categoria existe
        CategoriaQuarto categoria = categoriaQuartoRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria com ID " + dto.getCategoriaId() + " não encontrada"));

        // Validar unicidade do quarto
        var existe = quartoRepository.findByNumeroQuartoAndNumeroAndarAndNumeroBloco(
                dto.getNumeroQuarto(),
                dto.getNumeroAndar(),
                dto.getNumeroBloco()
        );

        if (existe.isPresent()) {
            throw new BusinessRuleException("Já existe um quarto com bloco " + dto.getNumeroBloco() +
                    ", andar " + dto.getNumeroAndar() + " e número " + dto.getNumeroQuarto());
        }

        Quarto quarto = new Quarto();
        quarto.setNumeroBloco(dto.getNumeroBloco());
        quarto.setNumeroAndar(dto.getNumeroAndar());
        quarto.setNumeroQuarto(dto.getNumeroQuarto());
        quarto.setCategoria(categoria);
        quarto.setStatus(StatusQuarto.DISPONIVEL);
        quarto.setNumeroCamasCasal(dto.getNumeroCamasCasal() != null ? dto.getNumeroCamasCasal() : 0);
        quarto.setNumeroCamasSolteiro(dto.getNumeroCamasSolteiro() != null ? dto.getNumeroCamasSolteiro() : 0);
        quarto.setAtivo(true);

        Quarto salvo = quartoRepository.save(quarto);
        return converterParaDto(salvo);
    }

    @Override
    @Transactional(readOnly = true)
    public QuartoDto obterPorId(Long id) {
        Quarto quarto = quartoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quarto com ID " + id + " não encontrado"));
        return converterParaDto(quarto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuartoDto> listar() {
        return quartoRepository.findAll()
                .stream()
                .map(this::converterParaDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuartoDto> listarAtivos() {
        return quartoRepository.findByAtivo(true)
                .stream()
                .map(this::converterParaDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuartoDto> listarPorStatus(StatusQuarto status) {
        return quartoRepository.findByStatus(status)
                .stream()
                .map(this::converterParaDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuartoDto> listarPorCategoria(Long categoriaId) {
        return quartoRepository.findByCategoriaId(categoriaId)
                .stream()
                .map(this::converterParaDto)
                .collect(Collectors.toList());
    }

    @Override
    public QuartoDto atualizar(Long id, QuartoDto dto) {
        Quarto quarto = quartoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quarto com ID " + id + " não encontrado"));

        if (dto.getNumeroCamasCasal() != null) {
            quarto.setNumeroCamasCasal(dto.getNumeroCamasCasal());
        }
        if (dto.getNumeroCamasSolteiro() != null) {
            quarto.setNumeroCamasSolteiro(dto.getNumeroCamasSolteiro());
        }

        Quarto atualizado = quartoRepository.save(quarto);
        return converterParaDto(atualizado);
    }

    @Override
    public QuartoDto alterarStatus(Long id, StatusQuarto novoStatus) {
        Quarto quarto = quartoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quarto com ID " + id + " não encontrado"));

        quarto.setStatus(novoStatus);

        Quarto atualizado = quartoRepository.save(quarto);
        return converterParaDto(atualizado);
    }

    @Override
    public void inativar(Long id) {
        Quarto quarto = quartoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quarto com ID " + id + " não encontrado"));
        quarto.setAtivo(false);
        quartoRepository.save(quarto);
    }

    @Override
    public void deletar(Long id) {
        if (!quartoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Quarto com ID " + id + " não encontrado");
        }
        quartoRepository.deleteById(id);
    }

    @Override
    public Page<RelatorioOcupacaoQuartoDto> relatorioOcupacaoQuartos(Long categoriaId, StatusQuarto status, Pageable pageable) {
        List<Quarto> quartos;
        
        // Filtrar por categoria e status
        if (categoriaId != null && status != null) {
            quartos = quartoRepository.findAll().stream()
                    .filter(q -> q.getCategoria().getId().equals(categoriaId))
                    .filter(q -> q.getStatus().equals(status))
                    .collect(Collectors.toList());
        } else if (categoriaId != null) {
            quartos = quartoRepository.findAll().stream()
                    .filter(q -> q.getCategoria().getId().equals(categoriaId))
                    .collect(Collectors.toList());
        } else if (status != null) {
            quartos = quartoRepository.findByStatus(status);
        } else {
            quartos = quartoRepository.findAll();
        }
        
        // Converter para DTO
        List<RelatorioOcupacaoQuartoDto> dtos = quartos.stream()
                .map(quarto -> new RelatorioOcupacaoQuartoDto(
                        quarto.getNumeroQuarto(),
                        quarto.getNumeroBloco(),
                        quarto.getNumeroAndar(),
                        quarto.getCategoria() != null ? quarto.getCategoria().getNome() : "N/A",
                        quarto.getStatus().toString()
                ))
                .collect(Collectors.toList());
        
        // Ordenar conforme solicitado no Pageable (por bloco ou andar)
        if (pageable.getSort().isSorted()) {
            dtos.sort((q1, q2) -> {
                String sortProperty = pageable.getSort().iterator().next().getProperty();
                if ("bloco".equals(sortProperty)) {
                    return q1.getBloco().compareTo(q2.getBloco());
                } else if ("andar".equals(sortProperty)) {
                    return q1.getAndar().compareTo(q2.getAndar());
                }
                return 0;
            });
        }
        
        // Aplicar paginação manualmente
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), dtos.size());
        List<RelatorioOcupacaoQuartoDto> paginatedList = dtos.subList(start, end);
        
        return new PageImpl<>(paginatedList, pageable, dtos.size());
    }

    private QuartoDto converterParaDto(Quarto quarto) {
        return new QuartoDto(
                quarto.getId(),
                quarto.getNumeroBloco(),
                quarto.getNumeroAndar(),
                quarto.getNumeroQuarto(),
                quarto.getStatus().name(),
                quarto.getCategoria().getId(),
                quarto.getCategoria().getNome(),
                quarto.getAtivo()
        );
    }
}
