package br.ifs.cads.api.hotel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuartoDto {
    private Long id;
    private Integer numeroBloco;
    private Integer numeroAndar;
    private Integer numeroQuarto;
    private String status;
    private Long categoriaId;
    private String categoriaNome;
    private Integer numeroCamasCasal;
    private Integer numeroCamasSolteiro;
    private Boolean ativo;

    public QuartoDto(Long id, Integer numeroBloco, Integer numeroAndar, Integer numeroQuarto,
                    String status, Long categoriaId, String categoriaNome, Boolean ativo) {
        this.id = id;
        this.numeroBloco = numeroBloco;
        this.numeroAndar = numeroAndar;
        this.numeroQuarto = numeroQuarto;
        this.status = status;
        this.categoriaId = categoriaId;
        this.categoriaNome = categoriaNome;
        this.ativo = ativo;
    }
}
