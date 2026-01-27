package br.ifs.cads.api.hotel.service;

import br.ifs.cads.api.hotel.dto.UsuarioDto;
import br.ifs.cads.api.hotel.enums.PapelUsuario;

import java.util.List;

public interface UsuarioService {
    UsuarioDto criar(UsuarioDto dto);
    UsuarioDto obterPorId(Long id);
    UsuarioDto obterPorEmail(String email);
    List<UsuarioDto> listar();
    List<UsuarioDto> listarAtivos();
    List<UsuarioDto> listarPorPapel(PapelUsuario papel);
    List<UsuarioDto> listarPorPapelAtivos(PapelUsuario papel);
    UsuarioDto atualizar(Long id, UsuarioDto dto);
    void inativar(Long id);
    void deletar(Long id);
    boolean verificarEmailExistente(String email);
}
