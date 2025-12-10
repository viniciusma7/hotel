package br.ifs.cads.api.hotel.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record EstadoDto(
        Long id,

        @NotBlank(message = "UF é obrigatório")
        @Length(max = 2, message = "UF deve conter apenas 2 caractéres.")
        String uf
){}
