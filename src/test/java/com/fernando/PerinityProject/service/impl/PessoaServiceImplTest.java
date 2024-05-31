package com.fernando.PerinityProject.service.impl;

import com.fernando.PerinityProject.exceptions.ResourceNotFoundException;
import com.fernando.PerinityProject.model.Pessoa;
import com.fernando.PerinityProject.model.Tarefa;
import com.fernando.PerinityProject.model.dto.PessoaHorasGastasDTO;
import com.fernando.PerinityProject.model.dto.PessoaMediaHorasGastasDTO;
import com.fernando.PerinityProject.repositories.PessoaRepository;
import com.fernando.PerinityProject.repositories.TarefaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class PessoaServiceImplTest {

    @InjectMocks
    private PessoaServiceImpl pessoaService;

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private TarefaRepository tarefaRepository;

    private Pessoa pessoa;
    private Pessoa pessoa2;
    private Pessoa updatePessoa;
    private List<Pessoa> pessoas;
    private Tarefa tarefa;
    private Tarefa tarefa2;
    private Tarefa tarefa3;
    private List<Tarefa> tarefas;
    private LocalDate startDate;
    private LocalDate endDate;

    @BeforeEach
    void setUp() {
        startPessoa();
    }

    @Test
    void whenAdicionarPessoaThenReturnAnPessoa() {
        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(pessoa);

        Pessoa resultado = pessoaService.adicionarPessoa(pessoa);

        assertNotNull(resultado);
        verify(pessoaRepository).save(pessoa);
    }

    @Test
    void whenAlterarPessoaThenReturnPessoaChanged() {
        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.of(pessoa));
        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(updatePessoa);

        Pessoa resultado = pessoaService.alterarPessoa(pessoa, 1L);

        assertNotNull(resultado);
        assertEquals(updatePessoa.getNome(), resultado.getNome());
        assertEquals(updatePessoa.getDepartamento(), resultado.getDepartamento());
        verify(pessoaRepository).save(any(Pessoa.class));
        verify(pessoaRepository).findById(1L);
    }

    @Test
    void whenListarPessoasThenReturnPessoaDepartamentoAndTotalHorasGastas() {
        when(pessoaRepository.findAll()).thenReturn(pessoas);
        when(tarefaRepository.findByPessoa(pessoa)).thenReturn(Collections.singletonList(tarefa));
        when(tarefaRepository.findByPessoa(pessoa2)).thenReturn(Collections.singletonList(tarefa2));

        List<PessoaHorasGastasDTO> resultado = pessoaService.listarPessoas();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        PessoaHorasGastasDTO dto1 = resultado.get(0);
        PessoaHorasGastasDTO dto2 = resultado.get(1);

        assertEquals("Test", dto1.getNome());
        assertEquals("Departamento Teste", dto1.getDepartamento());
        assertEquals(5, dto1.getTotalHorasGastas());

        assertEquals("Test 2", dto2.getNome());
        assertEquals("Departamento Teste 2", dto2.getDepartamento());
        assertEquals(5, dto2.getTotalHorasGastas());

        verify(pessoaRepository, times(1)).findAll();
        verify(tarefaRepository, times(1)).findByPessoa(pessoa);
        verify(tarefaRepository, times(1)).findByPessoa(pessoa2);
    }

    @Test
    void whenBuscarPessoaNomePeriodoThenReturnListPessoasMediaHorasGastas() {
        when(tarefaRepository.findByNomeAndPeriodo(anyString(), any(LocalDate.class), any(LocalDate.class))).thenReturn(tarefas);

        List<PessoaMediaHorasGastasDTO> resultado = pessoaService.buscarPessoaNomePeriodo("Test", startDate, endDate);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());

        PessoaMediaHorasGastasDTO dto = resultado.get(0);

        assertEquals("Test", dto.getNome());
        assertEquals(startDate + " to " + endDate, dto.getPeriodo());
        assertEquals(5, dto.getMediaHorasGastas());
        verify(tarefaRepository, times(1)).findByNomeAndPeriodo("Test", startDate, endDate);
    }

    @Test
    void whenListarTodasPessoasThenReturnTodasPessoas() {
        when(pessoaRepository.findAll()).thenReturn(pessoas);

        List<Pessoa> resultado = pessoaService.listarTodasPessoas();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(pessoaRepository).findAll();
    }

    @Test
    void whenDeleteAnPersonThenVerifyIfIsDeleted() {
        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.of(pessoa));

        pessoaService.deletarPessoa(1L);

        verify(pessoaRepository, times(1)).deleteById(1L);
    }

    @Test
    void whenDeletePessoaWithNonExistingIdThenThrowResourceNotFoundException() {
        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> pessoaService.deletarPessoa(1L));

        verify(pessoaRepository, times(1)).findById(1L);
        verify(pessoaRepository, times(0)).deleteById(anyLong());
    }

    @Test
    void whenFindByIdThenReturnAnPessoaInstance() {
        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.of(pessoa));

        Pessoa resultado = pessoaService.findById(anyLong());

        assertNotNull(resultado);
        assertEquals(pessoa, resultado);
        assertEquals(Pessoa.class, resultado.getClass());
        assertEquals(1L, resultado.getId());
    }

    @Test
    void whenFindByIdThenReturnResourceNotFoundException() {
        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> pessoaService.findById(anyLong()));
    }

    private void startPessoa() {
        pessoa = new Pessoa(1L, "Test", "Departamento Teste", null);
        pessoa2 = new Pessoa(2L, "Test 2", "Departamento Teste 2", null);
        pessoas = List.of(pessoa, pessoa2);
        updatePessoa = new Pessoa(null, "Test Updated", "Departamento Atualizado", null);

        tarefa = new Tarefa(1L, "Procurar requisitos", "Procurar todos os requisitos", LocalDate.of(2024, 10, 1), 5, "PENDENTE", "Departamento Teste", pessoa);
        tarefa2 = new Tarefa(2L, "Procurar requisitos", "Procurar todos os requisitos", LocalDate.of(2024, 10, 1), 5, "PENDENTE", "Departamento Teste 2", pessoa2);
        tarefa3 = new Tarefa(3L, "Procurar requisitos", "Procurar todos os requisitos", LocalDate.of(2024, 10, 2), 5, "PENDENTE", "Departamento Teste", pessoa);
        tarefas = List.of(tarefa, tarefa3);

        startDate = LocalDate.of(2024, 5, 1);
        endDate = LocalDate.of(2024, 11, 3);
    }
}