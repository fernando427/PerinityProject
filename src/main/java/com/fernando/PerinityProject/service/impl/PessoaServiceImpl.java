package com.fernando.PerinityProject.service.impl;

import com.fernando.PerinityProject.exceptions.ResourceNotFoundException;
import com.fernando.PerinityProject.model.Pessoa;
import com.fernando.PerinityProject.model.Tarefa;
import com.fernando.PerinityProject.model.dto.PessoaHorasGastasDTO;
import com.fernando.PerinityProject.model.dto.PessoaMediaHorasGastasDTO;
import com.fernando.PerinityProject.repositories.PessoaRepository;
import com.fernando.PerinityProject.repositories.TarefaRepository;
import com.fernando.PerinityProject.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PessoaServiceImpl implements PessoaService {

    private final PessoaRepository pessoaRepository;
    private final TarefaRepository tarefaRepository;

    @Autowired
    public PessoaServiceImpl(PessoaRepository pessoaRepository,
                             TarefaRepository tarefaRepository) {
        this.pessoaRepository = pessoaRepository;
        this.tarefaRepository = tarefaRepository;
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
    public List<PessoaHorasGastasDTO> listarPessoas() {
        List<Pessoa> pessoas = pessoaRepository.findAll();

        return pessoas.stream().map(pessoa -> {
            int totalHorasGastas = tarefaRepository.findByPessoa(pessoa)
                    .stream()
                    .mapToInt(Tarefa::getDuracaoH)
                    .sum();
            return new PessoaHorasGastasDTO(pessoa.getNome(), pessoa.getDepartamento(), totalHorasGastas);
        }).collect(Collectors.toList());
    }

    @Override
    public List<PessoaMediaHorasGastasDTO> buscarPessoaNomePeriodo(String nome, LocalDate startDate, LocalDate endDate) {
        List<Tarefa> tarefas = tarefaRepository.findByNomeAndPeriodo(nome, startDate, endDate);

        double mediaHorasGastas = tarefas.stream()
                .mapToInt(Tarefa::getDuracaoH)
                .average()
                .orElse(0);

        return tarefas.stream()
                .map(tarefa -> new PessoaMediaHorasGastasDTO(
                        tarefa.getPessoa().getNome(),
                        startDate + " to " + endDate,
                        mediaHorasGastas))
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<Pessoa> listarTodasPessoas() {
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
