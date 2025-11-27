package br.ifs.cads.api.hotel.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "cidades")
public class Cidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", length = 255, unique = true, nullable = false)
    private String nome;

    @ManyToOne
    private Estado estado;

    public Cidade() {}

    public Cidade(Long id, String nome, Estado estado) {
        this.id = id;
        this.nome = nome;
        this.estado = estado;
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

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cidade cidade = (Cidade) o;

        return id != null && id.equals(cidade.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}

