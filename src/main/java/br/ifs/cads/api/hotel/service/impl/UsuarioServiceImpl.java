package br.ifs.cads.api.hotel.service.impl;

import br.ifs.cads.api.hotel.dto.UsuarioDto;
import br.ifs.cads.api.hotel.entity.Usuario;
import br.ifs.cads.api.hotel.enums.PapelUsuario;
import br.ifs.cads.api.hotel.exception.BusinessRuleException;
import br.ifs.cads.api.hotel.exception.ResourceNotFoundException;
import br.ifs.cads.api.hotel.repository.UsuarioRepository;
import br.ifs.cads.api.hotel.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UsuarioDto criar(UsuarioDto dto) {
        // Validar email único
        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new BusinessRuleException("Já existe um usuário com o email: " + dto.getEmail());
        }

        // Validar papel válido
        if (dto.getPapel() == null) {
            throw new BusinessRuleException("O papel do usuário é obrigatório");
        }

        Usuario usuario = new Usuario();
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        usuario.setPapel(PapelUsuario.valueOf(dto.getPapel()));
        usuario.setAtivo(true);

        Usuario salvo = usuarioRepository.save(usuario);
        return converterParaDto(salvo);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioDto obterPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário com ID " + id + " não encontrado"));
        return converterParaDto(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioDto obterPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário com email " + email + " não encontrado"));
        return converterParaDto(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDto> listar() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::converterParaDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDto> listarAtivos() {
        return usuarioRepository.findByAtivo(true)
                .stream()
                .map(this::converterParaDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDto> listarPorPapel(PapelUsuario papel) {
        return usuarioRepository.findByPapel(papel)
                .stream()
                .map(this::converterParaDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDto> listarPorPapelAtivos(PapelUsuario papel) {
        return usuarioRepository.findByPapelAndAtivo(papel, true)
                .stream()
                .map(this::converterParaDto)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioDto atualizar(Long id, UsuarioDto dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário com ID " + id + " não encontrado"));

        // Se o email foi alterado, verificar unicidade
        if (dto.getEmail() != null && !dto.getEmail().equals(usuario.getEmail())) {
            if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
                throw new BusinessRuleException("Já existe um usuário com o email: " + dto.getEmail());
            }
            usuario.setEmail(dto.getEmail());
        }

        if (dto.getSenha() != null) {
            usuario.setSenha(dto.getSenha());
        }

        if (dto.getPapel() != null) {
            usuario.setPapel(PapelUsuario.valueOf(dto.getPapel()));
        }

        Usuario atualizado = usuarioRepository.save(usuario);
        return converterParaDto(atualizado);
    }

    @Override
    public void inativar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário com ID " + id + " não encontrado"));
        usuario.setAtivo(false);
        usuarioRepository.save(usuario);
    }

    @Override
    public void deletar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuário com ID " + id + " não encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean verificarEmailExistente(String email) {
        return usuarioRepository.findByEmail(email).isPresent();
    }

    private UsuarioDto converterParaDto(Usuario usuario) {
        return new UsuarioDto(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getPapel().name(),
                usuario.getAtivo()
        );
    }
}
