package br.ifs.cads.api.hotel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaQuartoDto {
    private Long id;
    private String nome;
    private String descricao;
    private Double valorDiaria;
    private Integer maxHospedes;
    private String posicao;
    private Boolean ativo;
    private List<ComodidadeDto> comodidades;

    public CategoriaQuartoDto(Long id, String nome, String descricao, Double valorDiaria,
                             Integer maxHospedes, String posicao, Boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.valorDiaria = valorDiaria;
        this.maxHospedes = maxHospedes;
        this.posicao = posicao;
        this.ativo = ativo;
    }
}
