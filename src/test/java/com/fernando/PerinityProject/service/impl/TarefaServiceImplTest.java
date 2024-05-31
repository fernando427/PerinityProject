package com.fernando.PerinityProject.service.impl;

import com.fernando.PerinityProject.exceptions.ResourceNotEqualException;
import com.fernando.PerinityProject.exceptions.ResourceNotFoundException;
import com.fernando.PerinityProject.model.Pessoa;
import com.fernando.PerinityProject.model.Tarefa;
import com.fernando.PerinityProject.repositories.PessoaRepository;
import com.fernando.PerinityProject.repositories.TarefaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class TarefaServiceImplTest {

    @InjectMocks
    private TarefaServiceImpl tarefaService;

    @Mock
    private TarefaRepository tarefaRepository;

    @Mock
    private PessoaRepository pessoaRepository;

    private Tarefa tarefa;
    private Tarefa tarefa2;
    private List<Tarefa> tarefas;
    private Pageable pageable;
    private Pessoa pessoa;
    private Pessoa pessoaDifDep;

    @BeforeEach
    void setUp() {
        startPessoa();
    }

    @Test
    void whenAdicionarTarefaReturnAnTarefa() {
        when(tarefaRepository.save(any(Tarefa.class))).thenReturn(tarefa);

        Tarefa resultado = tarefaService.adicionarTarefa(tarefa);

        assertNotNull(resultado);
        verify(tarefaRepository).save(tarefa);
    }

    @Test
    void whenAlocarTarefaThenReturnAnTarefaAlocada() {
        when(tarefaRepository.findById(anyLong())).thenReturn(Optional.of(tarefa));
        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.of(pessoa));
        when(tarefaRepository.save(any(Tarefa.class))).thenReturn(tarefa);

        Tarefa resultado = tarefaService.alocarTarefa(1L, 1L);

        assertNotNull(resultado);
        assertEquals(pessoa, resultado.getPessoa());
        verify(tarefaRepository).findById(1L);
        verify(pessoaRepository).findById(1L);
        verify(tarefaRepository).save(tarefa);
    }

    @Test
    void whenAlocarTarefaAndTarefaNotFoundThenThrowException() {
        when(tarefaRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> tarefaService.alocarTarefa(1L, 1L));
    }

    @Test
    void whenAlocarTarefaAndPessoaNotFoundThenThrowException() {
        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> tarefaService.alocarTarefa(1L, 1L));
    }

    @Test
    void whenAlocarTarefaAndDifferentDepartmentsThenResourceThrowException() {
        when(tarefaRepository.findById(anyLong())).thenReturn(Optional.of(tarefa));
        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.of(pessoaDifDep));
        assertThrows(ResourceNotEqualException.class, () -> tarefaService.alocarTarefa(1L, 2L));
    }

    @Test
    void whenFinalizarTarefaThenReturnTarefaFinalizada() {
        when(tarefaRepository.findById(anyLong())).thenReturn(Optional.of(tarefa));
        when(tarefaRepository.save(any(Tarefa.class))).thenReturn(tarefa);

        Tarefa resultado = tarefaService.finalizarTarefa(1L);

        assertNotNull(resultado);
        assertEquals("FINALIZADA", resultado.getStatus());
        verify(tarefaRepository).findById(1L);
        verify(tarefaRepository).save(tarefa);
    }

    @Test
    void whenFinalizarTarefaAndTarefaNotFoundThenThrowResourceNotFoundException() {
        when(tarefaRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> tarefaService.finalizarTarefa(1L));
    }

    @Test
    void whenFinalizarTarefaIsFinalizadaThenThrowResourceNotEqualException() {
        tarefa.setStatus("FINALIZADA");
        when(tarefaRepository.findById(anyLong())).thenReturn(Optional.of(tarefa));
        assertThrows(ResourceNotEqualException.class, () -> tarefaService.finalizarTarefa(1L));
    }

    @Test
    void whenListarTarefasPendentesThenReturnAnListTarefas() {
        when(tarefaRepository.findTarefasPendentes(pageable)).thenReturn(tarefas);

        List<Tarefa> resultado = tarefaService.listarTarefasPendentes();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(tarefaRepository).findTarefasPendentes(pageable);
    }

    private void startPessoa() {
        tarefa = new Tarefa(1L, "Procurar requisitos", "Procurar todos os requisitos", LocalDate.of(2024, 10, 1), 5, "PENDENTE", "Departamento Teste", null);
        tarefa2 = new Tarefa(2L, "Procurar requisitos", "Procurar todos os requisitos", LocalDate.of(2024, 10, 1), 5, "PENDENTE", "Departamento Teste", null);
        tarefas = List.of(tarefa, tarefa2);
        pageable = PageRequest.of(0, 3);

        pessoa = new Pessoa(1L, "Test", "Departamento Teste", null);
        pessoaDifDep = new Pessoa(2L, "Test", "Departamento Test Outro", null);
    }
}