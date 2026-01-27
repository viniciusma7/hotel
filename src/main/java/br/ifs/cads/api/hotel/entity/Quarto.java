package br.ifs.cads.api.hotel.entity;

import br.ifs.cads.api.hotel.enums.StatusQuarto;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "quartos")
public class Quarto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_bloco", nullable = false)
    private Integer numeroBloco;

    @Column(name = "numero_andar", nullable = false)
    private Integer numeroAndar;

    @Column(name = "numero_quarto", nullable = false)
    private Integer numeroQuarto;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusQuarto status = StatusQuarto.DISPONIVEL;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private CategoriaQuarto categoria;

    @Column(name = "numero_camas_casal")
    private Integer numeroCamasCasal = 0;

    @Column(name = "numero_camas_solteiro")
    private Integer numeroCamasSolteiro = 0;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

    public Quarto() {}

    public Quarto(Integer numeroBloco, Integer numeroAndar, Integer numeroQuarto,
                  CategoriaQuarto categoria) {
        this.numeroBloco = numeroBloco;
        this.numeroAndar = numeroAndar;
        this.numeroQuarto = numeroQuarto;
        this.categoria = categoria;
        this.status = StatusQuarto.DISPONIVEL;
        this.ativo = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroBloco() {
        return numeroBloco;
    }

    public void setNumeroBloco(Integer numeroBloco) {
        this.numeroBloco = numeroBloco;
    }

    public Integer getNumeroAndar() {
        return numeroAndar;
    }

    public void setNumeroAndar(Integer numeroAndar) {
        this.numeroAndar = numeroAndar;
    }

    public Integer getNumeroQuarto() {
        return numeroQuarto;
    }

    public void setNumeroQuarto(Integer numeroQuarto) {
        this.numeroQuarto = numeroQuarto;
    }

    public StatusQuarto getStatus() {
        return status;
    }

    public void setStatus(StatusQuarto status) {
        this.status = status;
    }

    public CategoriaQuarto getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaQuarto categoria) {
        this.categoria = categoria;
    }

    public Integer getNumeroCamasCasal() {
        return numeroCamasCasal;
    }

    public void setNumeroCamasCasal(Integer numeroCamasCasal) {
        this.numeroCamasCasal = numeroCamasCasal;
    }

    public Integer getNumeroCamasSolteiro() {
        return numeroCamasSolteiro;
    }

    public void setNumeroCamasSolteiro(Integer numeroCamasSolteiro) {
        this.numeroCamasSolteiro = numeroCamasSolteiro;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quarto quarto = (Quarto) o;
        return Objects.equals(id, quarto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Quarto{" +
                "id=" + id +
                ", numeroBloco=" + numeroBloco +
                ", numeroAndar=" + numeroAndar +
                ", numeroQuarto=" + numeroQuarto +
                ", status=" + status +
                ", categoria=" + categoria +
                ", ativo=" + ativo +
                '}';
    }
}
