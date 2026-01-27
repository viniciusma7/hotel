package br.ifs.cads.api.hotel.repository;

import br.ifs.cads.api.hotel.entity.Hospede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HospedeRepository extends JpaRepository<Hospede, Long> {
    Optional<Hospede> findByCpf(String cpf);
    List<Hospede> findByAtivo(Boolean ativo);
    List<Hospede> findByNomeContainingIgnoreCase(String nome);
    List<Hospede> findByCidadeId(Long cidadeId);
    List<Hospede> findByUsuarioId(Long usuarioId);
    List<Hospede> findByAtivoAndCidadeId(Boolean ativo, Long cidadeId);
}
