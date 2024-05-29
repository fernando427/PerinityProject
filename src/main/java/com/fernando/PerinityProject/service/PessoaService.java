package com.fernando.PerinityProject.service;

import com.fernando.PerinityProject.model.Pessoa;

import java.util.List;

public interface PessoaService {
    Pessoa adicionarPessoa(Pessoa pessoa);
    Pessoa alterarPessoa(Pessoa pessoa, Long id);
    List<Pessoa> findAll();
    void deletarPessoa(Long id);
}
