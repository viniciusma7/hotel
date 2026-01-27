package br.ifs.cads.api.hotel.repository;

import br.ifs.cads.api.hotel.entity.Quarto;
import br.ifs.cads.api.hotel.enums.StatusQuarto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuartoRepository extends JpaRepository<Quarto, Long> {
    Optional<Quarto> findByNumeroQuartoAndNumeroAndarAndNumeroBloco(Integer numero, Integer andar, Integer bloco);
    List<Quarto> findByStatus(StatusQuarto status);
    List<Quarto> findByAtivo(Boolean ativo);
    List<Quarto> findByCategoriaId(Long categoriaId);
    List<Quarto> findByStatusAndAtivo(StatusQuarto status, Boolean ativo);
}
