package br.ifs.cads.api.hotel.dto;

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

    public QuartoDto() {}

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getCategoriaNome() {
        return categoriaNome;
    }

    public void setCategoriaNome(String categoriaNome) {
        this.categoriaNome = categoriaNome;
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
    public String toString() {
        return "QuartoDto{" +
                "id=" + id +
                ", numeroBloco=" + numeroBloco +
                ", numeroAndar=" + numeroAndar +
                ", numeroQuarto=" + numeroQuarto +
                ", status='" + status + '\'' +
                ", categoriaId=" + categoriaId +
                ", categoriaNome='" + categoriaNome + '\'' +
                ", ativo=" + ativo +
                '}';
    }
}
