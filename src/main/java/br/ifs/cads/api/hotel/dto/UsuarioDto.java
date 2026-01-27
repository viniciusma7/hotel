package br.ifs.cads.api.hotel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto {
    private Long id;
    private String email;
    private String senha;
    private String papel;
    private Boolean ativo;

    public UsuarioDto(Long id, String email, String papel, Boolean ativo) {
        this.id = id;
        this.email = email;
        this.papel = papel;
        this.ativo = ativo;
    }
}
