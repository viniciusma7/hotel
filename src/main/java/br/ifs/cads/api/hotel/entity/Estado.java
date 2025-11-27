package br.ifs.cads.api.hotel.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "estados")
public class Estado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uf", length = 2, nullable = false, unique = true)
    private String uf;

    @OneToMany(mappedBy = "estado", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cidade> cidades;

    public Estado() {}

    public Estado(Long id, String uf) {
        this.id = id;
        this.uf = uf;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public List<Cidade> getCidades() {
        return cidades;
    }

    public void setCidades(List<Cidade> cidades) {
        this.cidades = cidades;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Estado estado = (Estado) o;

        return id != null && id.equals(estado.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
