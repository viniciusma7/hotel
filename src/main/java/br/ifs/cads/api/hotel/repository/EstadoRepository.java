package br.ifs.cads.api.hotel.repository;

import br.ifs.cads.api.hotel.entity.Estado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstadoRepository extends JpaRepository<Estado, Long> {
    Optional<Estado> findByUf(String uf);
}
