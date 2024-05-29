package com.fernando.PerinityProject.service;

import com.fernando.PerinityProject.model.Pessoa;
import com.fernando.PerinityProject.model.dto.PessoaHorasGastasDTO;

import java.util.List;

public interface PessoaService {
    Pessoa adicionarPessoa(Pessoa pessoa);
    Pessoa alterarPessoa(Pessoa pessoa, Long id);
    List<PessoaHorasGastasDTO> listarPessoas();
    void deletarPessoa(Long id);
}
