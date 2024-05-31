package com.fernando.PerinityProject.controller;

import com.fernando.PerinityProject.model.Pessoa;
import com.fernando.PerinityProject.model.Tarefa;
import com.fernando.PerinityProject.service.TarefaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
class TarefaControllerTest {

    @InjectMocks
    private TarefaController tarefaController;

    @Mock
    private TarefaService tarefaService;

    private Tarefa tarefa;
    private Tarefa tarefa2;
    private Pessoa pessoa;
    private Tarefa tarefaComPessoa;
    private Tarefa tarefaFinalizada;
    private List<Tarefa> tarefas;

    @BeforeEach
    void setUp() {
        startTarefa();
    }

    @Test
    void whenAdicionarTarefaThenReturnTarefaAdicionada() {
        when(tarefaService.adicionarTarefa(any())).thenReturn(tarefa);

        ResponseEntity<Tarefa> resultado = tarefaController.adicionarTarefa(tarefa);

        assertNotNull(resultado);
        assertEquals(HttpStatus.CREATED, resultado.getStatusCode());
        assertEquals(ResponseEntity.class, resultado.getClass());
    }

    @Test
    void whenAlocarTarefaThenReturnTarefaAlocada() {
        when(tarefaService.alocarTarefa(anyLong(), anyLong())).thenReturn(tarefaComPessoa);

        ResponseEntity<Tarefa> resultado = tarefaController.alocarTarefa(3L, 1L);

        assertNotNull(resultado);
        assertNotNull(resultado.getBody());
        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(ResponseEntity.class, resultado.getClass());
        assertEquals(Tarefa.class, resultado.getBody().getClass());
    }

    @Test
    void whenFinalizarTarefaThenReturnTarefaFinalizada() {
        when(tarefaService.finalizarTarefa(anyLong())).thenReturn(tarefaFinalizada);

        ResponseEntity<Tarefa> resultado = tarefaController.finalizarTarefa(1L);

        assertNotNull(resultado);
        assertNotNull(resultado.getBody());
        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(ResponseEntity.class, resultado.getClass());
        assertEquals("FINALIZADA", resultado.getBody().getStatus());
        assertEquals(Tarefa.class, resultado.getBody().getClass());
    }

    @Test
    void whenListarTarefasPendentesThenReturnListOfTarefas() {
        when(tarefaService.listarTarefasPendentes()).thenReturn(tarefas);

        ResponseEntity<List<Tarefa>> resultado = tarefaController.listarTarefasPendentes();

        assertNotNull(resultado);
        assertNotNull(resultado.getBody());
        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(ResponseEntity.class, resultado.getClass());
        assertEquals(Tarefa.class, resultado.getBody().get(0).getClass());
    }

    @Test
    void whenListarTodasTarefasThenReturnListOfTodasTarefas() {
        when(tarefaService.listarTodasTarefas()).thenReturn(tarefas);

        ResponseEntity<List<Tarefa>> resultado = tarefaController.listarTodasTarefas();

        assertNotNull(resultado);
        assertNotNull(resultado.getBody());
        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(ResponseEntity.class, resultado.getClass());
        assertEquals(Tarefa.class, resultado.getBody().get(0).getClass());
    }

    private void startTarefa() {
        pessoa = new Pessoa(1L, "Tomas", "Departamento Teste", null);
        tarefa = new Tarefa(1L, "Procurar requisitos", "Procurar todos os requisitos", LocalDate.of(2024, 10, 1), 5, "PENDENTE", "Departamento Teste", null);
        tarefa2 = new Tarefa(2L, "Procurar requisitos", "Procurar todos os requisitos", LocalDate.of(2024, 10, 1), 5, "PENDENTE", "Departamento Teste", null);
        tarefaComPessoa = new Tarefa(3L, "Procurar requisitos", "Procurar todos os requisitos", LocalDate.of(2024, 10, 1), 5, "PENDENTE", "Departamento Teste", pessoa);
        tarefaFinalizada = new Tarefa(4L, "Procurar requisitos", "Procurar todos os requisitos", LocalDate.of(2024, 10, 1), 5, "FINALIZADA", "Departamento Teste", null);
        tarefas = List.of(tarefa, tarefa2);
    }
}