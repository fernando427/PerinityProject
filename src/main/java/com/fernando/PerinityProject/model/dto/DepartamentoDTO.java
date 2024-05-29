package com.fernando.PerinityProject.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartamentoDTO {
    private String nome;
    private Long qntPessoas;
    private Long qntTarefas;
}
