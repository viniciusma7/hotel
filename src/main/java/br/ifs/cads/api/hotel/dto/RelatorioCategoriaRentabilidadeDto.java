package br.ifs.cads.api.hotel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioCategoriaRentabilidadeDto {

    private String categoria;
    private Long totalReservas;
    private Double totalFaturado;
}
