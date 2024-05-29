package com.fernando.PerinityProject.controller;

import com.fernando.PerinityProject.model.Tarefa;
import com.fernando.PerinityProject.service.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    private final TarefaService tarefaService;

    @Autowired
    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @PostMapping
    public ResponseEntity<Tarefa> adicionarTarefa(@RequestBody Tarefa tarefa) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tarefaService.adicionarTarefa(tarefa));
    }

    @PutMapping("/{tarefaId}/alocar/{pessoaId}")
    public ResponseEntity<Tarefa> alterarPessoa(@PathVariable Long tarefaId, @PathVariable Long pessoaId) {
        return ResponseEntity.ok().body(tarefaService.alocarTarefa(tarefaId, pessoaId));
    }

    @PutMapping("/finalizar/{id}")
    public ResponseEntity<Tarefa> alterarPessoa(@PathVariable Long id) {
        return ResponseEntity.ok().body(tarefaService.finalizarTarefa(id));
    }
}
