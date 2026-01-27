package br.ifs.cads.api.hotel.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "comodidades")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class Comodidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "descricao", length = 255)
    private String descricao;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

    public Comodidade(Long id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.ativo = true;
    }
}
