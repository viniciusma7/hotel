package br.ifs.cads.api.hotel.service;

import br.ifs.cads.api.hotel.dto.QuartoDto;
import br.ifs.cads.api.hotel.enums.StatusQuarto;

import java.util.List;

public interface QuartoService {
    QuartoDto criar(QuartoDto dto);
    QuartoDto obterPorId(Long id);
    List<QuartoDto> listar();
    List<QuartoDto> listarAtivos();
    List<QuartoDto> listarPorStatus(StatusQuarto status);
    List<QuartoDto> listarPorCategoria(Long categoriaId);
    QuartoDto atualizar(Long id, QuartoDto dto);
    QuartoDto alterarStatus(Long id, StatusQuarto novoStatus);
    void inativar(Long id);
    void deletar(Long id);
}
