package br.ifs.cads.api.hotel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaDto {
    private Long id;
    private Long hospedeId;
    private String hospedeNome;
    private Long quartoId;
    private String quartoNumero;
    private String categoriaNome;
    private LocalDateTime dataReserva;
    private LocalDate dataCheckIn;
    private LocalDate dataCheckOut;
    private String statusReserva;
    private Double valorTotal;
    private String observacoes;
    private Boolean ativo;

    public ReservaDto(Long id, Long hospedeId, String hospedeNome, Long quartoId,
                     String quartoNumero, LocalDate dataCheckIn, LocalDate dataCheckOut,
                     String statusReserva, Double valorTotal) {
        this.id = id;
        this.hospedeId = hospedeId;
        this.hospedeNome = hospedeNome;
        this.quartoId = quartoId;
        this.quartoNumero = quartoNumero;
        this.dataCheckIn = dataCheckIn;
        this.dataCheckOut = dataCheckOut;
        this.statusReserva = statusReserva;
        this.valorTotal = valorTotal;
    }
}
