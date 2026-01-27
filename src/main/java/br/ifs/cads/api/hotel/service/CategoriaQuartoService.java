package br.ifs.cads.api.hotel.service;

import br.ifs.cads.api.hotel.dto.CategoriaQuartoDto;

import java.util.List;

public interface CategoriaQuartoService {
    CategoriaQuartoDto criar(CategoriaQuartoDto dto);
    CategoriaQuartoDto obterPorId(Long id);
    List<CategoriaQuartoDto> listar();
    List<CategoriaQuartoDto> listarAtivas();
    CategoriaQuartoDto atualizar(Long id, CategoriaQuartoDto dto);
    void inativar(Long id);
    void deletar(Long id);
}
