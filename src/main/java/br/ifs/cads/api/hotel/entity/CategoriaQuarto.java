package br.ifs.cads.api.hotel.entity;

import br.ifs.cads.api.hotel.enums.Posicao;
import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "categorias_quarto")
public class CategoriaQuarto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "descricao", length = 255)
    private String descricao;

    @Column(name = "valor_diaria", nullable = false)
    private Double valorDiaria;

    @Column(name = "max_hospedes", nullable = false)
    private Integer maxHospedes;

    @Enumerated(EnumType.STRING)
    @Column(name = "posicao", nullable = false)
    private Posicao posicao;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Quarto> quartos;

    @ManyToMany
    @JoinTable(
            name = "categoria_comodidade",
            joinColumns = @JoinColumn(name = "categoria_id"),
            inverseJoinColumns = @JoinColumn(name = "comodidade_id")
    )
    private List<Comodidade> comodidades;

    public CategoriaQuarto() {}

    public CategoriaQuarto(Long id, String nome, String descricao, Double valorDiaria,
                          Integer maxHospedes, Posicao posicao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.valorDiaria = valorDiaria;
        this.maxHospedes = maxHospedes;
        this.posicao = posicao;
        this.ativo = true;
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

    public Posicao getPosicao() {
        return posicao;
    }

    public void setPosicao(Posicao posicao) {
        this.posicao = posicao;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public List<Quarto> getQuartos() {
        return quartos;
    }

    public void setQuartos(List<Quarto> quartos) {
        this.quartos = quartos;
    }

    public List<Comodidade> getComodidades() {
        return comodidades;
    }

    public void setComodidades(List<Comodidade> comodidades) {
        this.comodidades = comodidades;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoriaQuarto that = (CategoriaQuarto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "CategoriaQuarto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", valorDiaria=" + valorDiaria +
                ", maxHospedes=" + maxHospedes +
                ", posicao=" + posicao +
                ", ativo=" + ativo +
                '}';
    }
}
