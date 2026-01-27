package br.ifs.cads.api.hotel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioOcupacaoQuartoDto {
    private Integer numeroQuarto;
    private Integer bloco;
    private Integer andar;
    private String categoria;
    private String statusAtual;
}
