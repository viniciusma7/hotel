package br.ifs.cads.api.hotel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CidadeDto (
    Long id,

    @NotBlank(message = "Nome da Cidade é obrigatório.")
    @Size(max = 255, message = "O nome da cidade não pode exceder 255 Caractéres")
    String nome,

    Long estado
) {}

