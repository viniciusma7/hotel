package br.ifs.cads.api.hotel.service;

import br.ifs.cads.api.hotel.dto.ComodidadeDto;
import br.ifs.cads.api.hotel.entity.Comodidade;

import java.util.List;

public interface ComodidadeService {
    ComodidadeDto criar(ComodidadeDto dto);
    ComodidadeDto obterPorId(Long id);
    List<ComodidadeDto> listar();
    List<ComodidadeDto> listarAtivas();
    ComodidadeDto atualizar(Long id, ComodidadeDto dto);
    void inativar(Long id);
    void deletar(Long id);
}
