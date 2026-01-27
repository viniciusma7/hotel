package br.ifs.cads.api.hotel.dto;

public class UsuarioDto {
    private Long id;
    private String email;
    private String senha;
    private String papel;
    private Boolean ativo;

    public UsuarioDto() {}

    public UsuarioDto(Long id, String email, String papel, Boolean ativo) {
        this.id = id;
        this.email = email;
        this.papel = papel;
        this.ativo = ativo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getPapel() {
        return papel;
    }

    public void setPapel(String papel) {
        this.papel = papel;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return "UsuarioDto{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", papel='" + papel + '\'' +
                ", ativo=" + ativo +
                '}';
    }
}
