package br.ifs.cads.api.hotel.repository;

import br.ifs.cads.api.hotel.entity.CategoriaQuarto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaQuartoRepository extends JpaRepository<CategoriaQuarto, Long> {
    Optional<CategoriaQuarto> findByNome(String nome);
    List<CategoriaQuarto> findByAtivo(Boolean ativo);
}
