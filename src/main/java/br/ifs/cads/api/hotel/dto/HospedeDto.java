package br.ifs.cads.api.hotel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HospedeDto {
    private Long id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String telefone;
    private Long cidadeId;
    private String cidadeNome;
    private Long usuarioId;
    private String usuarioEmail;
    private Boolean ativo;

    public HospedeDto(Long id, String nome, String cpf, LocalDate dataNascimento,
                     String telefone, Long cidadeId, String cidadeNome, Boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.cidadeId = cidadeId;
        this.cidadeNome = cidadeNome;
        this.ativo = ativo;
    }
}
