package br.ifs.cads.api.hotel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioReservaPeriodoDto {
    private Long idReserva;
    private String nomeHospede;
    private String categoriaQuarto;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String statusReserva;
}
