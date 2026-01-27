package br.ifs.cads.api.hotel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioDto {
    private Long id;
    private String nome;
    private String cpf;
    private String cargo;
    private LocalDate dataAdmissao;
    private LocalDate dataDemissao;
    private Long usuarioId;
    private String usuarioEmail;
    private Boolean ativo;

    public FuncionarioDto(Long id, String nome, String cpf, String cargo, 
                         LocalDate dataAdmissao, Boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.cargo = cargo;
        this.dataAdmissao = dataAdmissao;
        this.ativo = ativo;
    }
}
