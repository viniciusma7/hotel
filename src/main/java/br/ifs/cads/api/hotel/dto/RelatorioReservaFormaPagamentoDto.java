package br.ifs.cads.api.hotel.dto;

import br.ifs.cads.api.hotel.enums.FormaPagamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioReservaFormaPagamentoDto {

    private FormaPagamento formaPagamento;
    private Long quantidadeReservas;
    private Double valorTotalRecebido;
}
