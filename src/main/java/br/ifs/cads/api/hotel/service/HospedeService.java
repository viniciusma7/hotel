package br.ifs.cads.api.hotel.service;

import br.ifs.cads.api.hotel.dto.HospedeDto;
import br.ifs.cads.api.hotel.dto.RelatorioHospedeAtivoDto;

import java.util.List;

public interface HospedeService {
    HospedeDto criar(HospedeDto dto);
    HospedeDto obterPorId(Long id);
    HospedeDto obterPorCpf(String cpf);
    List<HospedeDto> listar();
    List<HospedeDto> listarAtivos();
    List<HospedeDto> buscarPorNome(String nome);
    List<HospedeDto> listarPorCidade(Long cidadeId);
    List<HospedeDto> listarPorUsuario(Long usuarioId);
    HospedeDto atualizar(Long id, HospedeDto dto);
    void inativar(Long id);
    void deletar(Long id);
    void associarUsuario(Long hospedeId, Long usuarioId);
    void desassociarUsuario(Long hospedeId);

    // UC-04: Relatório de Hóspedes Ativos
    List<RelatorioHospedeAtivoDto> relatorioHospedesAtivos();
}
