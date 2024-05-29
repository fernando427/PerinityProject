package com.fernando.PerinityProject.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PessoaHorasGastasDTO {
    private String nome;
    private String departamento;
    private int totalHorasGastas;
}
