package br.ifs.cads.api.hotel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioHospedeAtivoDto {

    private String nomeHospede;
    private String email;
    private String telefone;
    private String cidade;
    private String estado;
}
