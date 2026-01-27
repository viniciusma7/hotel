package br.ifs.cads.api.hotel.repository;

import br.ifs.cads.api.hotel.entity.Usuario;
import br.ifs.cads.api.hotel.enums.PapelUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findByPapel(PapelUsuario papel);
    List<Usuario> findByAtivo(Boolean ativo);
    List<Usuario> findByPapelAndAtivo(PapelUsuario papel, Boolean ativo);
}
