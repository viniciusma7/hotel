package br.ifs.cads.api.hotel.entity;

import br.ifs.cads.api.hotel.enums.StatusQuarto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "quartos")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"categoria"})
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

    public Quarto(Integer numeroBloco, Integer numeroAndar, Integer numeroQuarto,
                  CategoriaQuarto categoria) {
        this.numeroBloco = numeroBloco;
        this.numeroAndar = numeroAndar;
        this.numeroQuarto = numeroQuarto;
        this.categoria = categoria;
        this.status = StatusQuarto.DISPONIVEL;
        this.ativo = true;
    }
}
