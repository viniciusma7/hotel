package br.ifs.cads.api.hotel.repository;

import br.ifs.cads.api.hotel.entity.Cancelamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CancelamentoRepository extends JpaRepository<Cancelamento, Long> {
    Optional<Cancelamento> findByReservaId(Long reservaId);
    List<Cancelamento> findByAtivo(Boolean ativo);
    List<Cancelamento> findByDataCancelamentoBetween(LocalDateTime dataInicio, LocalDateTime dataFim);
    List<Cancelamento> findByMotivoContainingIgnoreCase(String motivo);
}
