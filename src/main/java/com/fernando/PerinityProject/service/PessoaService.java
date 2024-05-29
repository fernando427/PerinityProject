package com.fernando.PerinityProject.service;

import com.fernando.PerinityProject.model.Pessoa;
import com.fernando.PerinityProject.model.dto.PessoaHorasGastasDTO;
import com.fernando.PerinityProject.model.dto.PessoaMediaHorasGastasDTO;

import java.time.LocalDate;
import java.util.List;

public interface PessoaService {
    Pessoa adicionarPessoa(Pessoa pessoa);
    Pessoa alterarPessoa(Pessoa pessoa, Long id);
    List<PessoaHorasGastasDTO> listarPessoas();
    List<PessoaMediaHorasGastasDTO> buscarPessoaNomePeriodo(String nome, LocalDate startDate, LocalDate endDate);
    void deletarPessoa(Long id);
}
