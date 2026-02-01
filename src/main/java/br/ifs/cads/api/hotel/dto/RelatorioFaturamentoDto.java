package br.ifs.cads.api.hotel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioFaturamentoDto {

    private Double totalBruto;
    private Double totalDescontos;
    private Double totalLiquido;
}
