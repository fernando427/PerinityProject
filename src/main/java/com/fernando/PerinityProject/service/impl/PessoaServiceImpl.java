package com.fernando.PerinityProject.service.impl;

import com.fernando.PerinityProject.exceptions.ResourceNotFoundException;
import com.fernando.PerinityProject.model.Pessoa;
import com.fernando.PerinityProject.repositories.PessoaRepository;
import com.fernando.PerinityProject.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PessoaServiceImpl implements PessoaService {

    private final PessoaRepository pessoaRepository;

    @Autowired
    public PessoaServiceImpl(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Override
    public Pessoa adicionarPessoa(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    @Override
    public Pessoa alterarPessoa(Pessoa pessoa, Long id) {
        Pessoa pExist = findById(id);
        pessoa.setId(pExist.getId());
        return pessoaRepository.save(pessoa);
    }

    @Override
    public List<Pessoa> findAll() {
        return pessoaRepository.findAll();
    }

    @Override
    public void deletarPessoa(Long id) {
        findById(id);
        pessoaRepository.deleteById(id);
    }

    public Pessoa findById(Long id) {
        Optional<Pessoa> result = pessoaRepository.findById(id);
        return result.orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada!"));
    }
}
