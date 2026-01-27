package br.ifs.cads.api.hotel.entity;

import br.ifs.cads.api.hotel.enums.Posicao;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "categorias_quarto")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"quartos", "comodidades"})
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
}
