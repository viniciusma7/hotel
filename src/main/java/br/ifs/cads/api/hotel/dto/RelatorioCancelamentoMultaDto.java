package br.ifs.cads.api.hotel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioCancelamentoMultaDto {
    private String nomeHospede;
    private String categoriaQuarto;
    private LocalDateTime dataCancelamento;
    private Double valorMulta;
}
