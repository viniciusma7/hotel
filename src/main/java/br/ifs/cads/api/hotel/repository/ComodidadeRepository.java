package br.ifs.cads.api.hotel.repository;

import br.ifs.cads.api.hotel.entity.Comodidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComodidadeRepository extends JpaRepository<Comodidade, Long> {
    Optional<Comodidade> findByNome(String nome);
    List<Comodidade> findByAtivo(Boolean ativo);
}
