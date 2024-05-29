package com.fernando.PerinityProject.config;

import com.fernando.PerinityProject.model.Pessoa;
import com.fernando.PerinityProject.model.Tarefa;
import com.fernando.PerinityProject.repositories.PessoaRepository;
import com.fernando.PerinityProject.repositories.TarefaRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class LocalConfig {

    private final PessoaRepository pessoaRepository;
    private final TarefaRepository tarefaRepository;

    @Autowired
    public LocalConfig(PessoaRepository pessoaRepository, TarefaRepository tarefaRepository) {
        this.pessoaRepository = pessoaRepository;
        this.tarefaRepository = tarefaRepository;
    }

    @PostConstruct
    public void startDB() {
        if (pessoaRepository.count() == 0) {
            Pessoa pessoaA = new Pessoa(null, "Tomas", "TI", null);
            Pessoa pessoaB = new Pessoa(null, "Agostinho", "Segurança", null);

            pessoaRepository.saveAll(List.of(pessoaA, pessoaB));
        }

        if (tarefaRepository.count() == 0) {
            Tarefa tarefaA = new Tarefa(null, "Procurar requisitos", "Procurar todos os requisitos", LocalDate.of(2024, 10, 1), 5, "PENDENTE", "TI", null);
            Tarefa tarefaB = new Tarefa(null, "Atualizar banco de dados", "Realizar atualização de drivers", LocalDate.of(2024, 6, 5), 7, "PENDENTE", "Segurança", null);
            Tarefa tarefaC = new Tarefa(null, "Desenvolvimento de requisitos", "Desenvolver todos os requisitos", LocalDate.of(2024, 6, 10), 15, "PENDENTE", "TI", null);
            Tarefa tarefaD = new Tarefa(null, "Criação de testes", "Criar testes das funcionalidades", LocalDate.of(2024, 8, 1), 20, "PENDENTE", "Segurança", null);

            tarefaRepository.saveAll(List.of(tarefaA, tarefaB, tarefaC, tarefaD));
        }
    }

}
