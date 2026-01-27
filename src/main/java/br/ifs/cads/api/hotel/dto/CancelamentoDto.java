package br.ifs.cads.api.hotel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CancelamentoDto {
    private Long id;
    private Long reservaId;
    private String hospedeNome;
    private String quartoNumero;
    private LocalDateTime dataCancelamento;
    private String motivo;
    private Double valorMulta;
    private String justificativa;
    private Boolean ativo;

    public CancelamentoDto(Long id, Long reservaId, String hospedeNome, 
                          String quartoNumero, LocalDateTime dataCancelamento,
                          String motivo, Double valorMulta) {
        this.id = id;
        this.reservaId = reservaId;
        this.hospedeNome = hospedeNome;
        this.quartoNumero = quartoNumero;
        this.dataCancelamento = dataCancelamento;
        this.motivo = motivo;
        this.valorMulta = valorMulta;
    }
}
