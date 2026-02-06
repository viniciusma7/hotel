package br.ifs.cads.api.hotel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioHistoricoHospedeDto {

    private Long idReserva;
    private LocalDate dataCheckIn;
    private LocalDate dataCheckOut;
    private String categoriaQuarto;
    private String statusReserva;
    private Double valorPago;
}
