package br.ifs.cads.api.hotel.dto;

import java.util.List;

public class CategoriaQuartoDto {
    private Long id;
    private String nome;
    private String descricao;
    private Double valorDiaria;
    private Integer maxHospedes;
    private String posicao;
    private Boolean ativo;
    private List<ComodidadeDto> comodidades;

    public CategoriaQuartoDto() {}

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValorDiaria() {
        return valorDiaria;
    }

    public void setValorDiaria(Double valorDiaria) {
        this.valorDiaria = valorDiaria;
    }

    public Integer getMaxHospedes() {
        return maxHospedes;
    }

    public void setMaxHospedes(Integer maxHospedes) {
        this.maxHospedes = maxHospedes;
    }

    public String getPosicao() {
        return posicao;
    }

    public void setPosicao(String posicao) {
        this.posicao = posicao;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public List<ComodidadeDto> getComodidades() {
        return comodidades;
    }

    public void setComodidades(List<ComodidadeDto> comodidades) {
        this.comodidades = comodidades;
    }

    @Override
    public String toString() {
        return "CategoriaQuartoDto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", valorDiaria=" + valorDiaria +
                ", maxHospedes=" + maxHospedes +
                ", posicao='" + posicao + '\'' +
                ", ativo=" + ativo +
                '}';
    }
}
