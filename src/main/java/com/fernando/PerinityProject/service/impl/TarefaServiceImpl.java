package com.fernando.PerinityProject.service.impl;

import com.fernando.PerinityProject.model.Tarefa;
import com.fernando.PerinityProject.repositories.TarefaRepository;
import com.fernando.PerinityProject.service.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TarefaServiceImpl implements TarefaService {

    private final TarefaRepository tarefaRepository;

    @Autowired
    public TarefaServiceImpl(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    @Override
    public Tarefa adicionarTarefa(Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }
}
