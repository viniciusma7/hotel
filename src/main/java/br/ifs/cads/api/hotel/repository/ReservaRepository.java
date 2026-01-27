package br.ifs.cads.api.hotel.repository;

import br.ifs.cads.api.hotel.entity.Reserva;
import br.ifs.cads.api.hotel.enums.StatusReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByHospedeId(Long hospedeId);
    List<Reserva> findByQuartoId(Long quartoId);
    List<Reserva> findByStatusReserva(StatusReserva statusReserva);
    List<Reserva> findByAtivo(Boolean ativo);
    List<Reserva> findByDataCheckInBetween(LocalDate dataInicio, LocalDate dataFim);
    List<Reserva> findByDataCheckOutBetween(LocalDate dataInicio, LocalDate dataFim);
    List<Reserva> findByHospedeIdAndStatusReserva(Long hospedeId, StatusReserva statusReserva);
    List<Reserva> findByQuartoIdAndStatusReserva(Long quartoId, StatusReserva statusReserva);
}
