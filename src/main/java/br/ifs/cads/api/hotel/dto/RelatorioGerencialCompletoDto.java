package br.ifs.cads.api.hotel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioGerencialCompletoDto {

    private Long totalReservas;
    private Long totalCancelamentos;
    private RelatorioFaturamentoDto faturamento;
    private Double ocupacaoMediaPercentual;
    private Page<RelatorioCategoriaRentabilidadeDto> categoriasMaisRentaveis;
}
