package com.fernando.PerinityProject.service.impl;

import com.fernando.PerinityProject.exceptions.ResourceNotEqualException;
import com.fernando.PerinityProject.exceptions.ResourceNotFoundException;
import com.fernando.PerinityProject.model.Pessoa;
import com.fernando.PerinityProject.model.Tarefa;
import com.fernando.PerinityProject.repositories.PessoaRepository;
import com.fernando.PerinityProject.repositories.TarefaRepository;
import com.fernando.PerinityProject.service.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TarefaServiceImpl implements TarefaService {

    private final TarefaRepository tarefaRepository;
    private final PessoaRepository pessoaRepository;

    @Autowired
    public TarefaServiceImpl(TarefaRepository tarefaRepository,
                             PessoaRepository pessoaRepository) {
        this.tarefaRepository = tarefaRepository;
        this.pessoaRepository = pessoaRepository;
    }

    @Override
    public Tarefa adicionarTarefa(Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }

    @Override
    public Tarefa alocarTarefa(Long tarefaid, Long pessoaId) {
        Tarefa tarefa = tarefaRepository.findById(tarefaid)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada!"));
        Pessoa pessoa = pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada!"));

        if(!tarefa.getDepartamento().equals(pessoa.getDepartamento())) {
            throw new ResourceNotEqualException("Pessoa e Tarefa não pertencem ao mesmo departamento!");
        }

        tarefa.setPessoa(pessoa);
        return tarefaRepository.save(tarefa);
    }

    @Override
    public Tarefa finalizarTarefa(Long id) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada!"));

        if(!tarefa.getStatus().equals("PENDENTE")) {
            throw new ResourceNotEqualException("A tarefa já está finalizada!");
        }

        tarefa.setStatus("FINALIZADA");
        return tarefaRepository.save(tarefa);
    }


}
