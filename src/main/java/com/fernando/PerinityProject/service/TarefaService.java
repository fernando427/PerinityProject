package com.fernando.PerinityProject.service;

import com.fernando.PerinityProject.model.Tarefa;

import java.util.List;

public interface TarefaService {
    Tarefa adicionarTarefa(Tarefa tarefa);
    Tarefa alocarTarefa(Long tarefaId, Long pessoaId);
    Tarefa finalizarTarefa(Long id);
    List<Tarefa> listarTodasTarefas();
    List<Tarefa> listarTarefasPendentes();
}
