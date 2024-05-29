package com.fernando.PerinityProject.service;

import com.fernando.PerinityProject.model.Tarefa;

public interface TarefaService {
    Tarefa adicionarTarefa(Tarefa tarefa);
    Tarefa alocarTarefa(Long tarefaId, Long pessoaId);
    Tarefa finalizarTarefa(Long id);
}
