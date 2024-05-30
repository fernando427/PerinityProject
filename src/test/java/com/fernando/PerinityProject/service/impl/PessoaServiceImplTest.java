package com.fernando.PerinityProject.service.impl;

import com.fernando.PerinityProject.exceptions.ResourceNotFoundException;
import com.fernando.PerinityProject.model.Pessoa;
import com.fernando.PerinityProject.repositories.PessoaRepository;
import com.fernando.PerinityProject.repositories.TarefaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

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

    @BeforeEach
    void setUp() {
        startPessoa();
    }

    @Test
    void whenAdicionarPessoaThenReturnAnPessoa() {
        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(pessoa);

        Pessoa response = pessoaService.adicionarPessoa(pessoa);

        assertNotNull(response);
        verify(pessoaRepository).save(pessoa);
    }

    @Test
    void alterarPessoa() {
    }

    @Test
    void listarPessoas() {
    }

    @Test
    void buscarPessoaNomePeriodo() {
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

        Pessoa response = pessoaService.findById(anyLong());

        assertNotNull(response);
        assertEquals(pessoa, response);
        assertEquals(Pessoa.class, response.getClass());
        assertEquals(1L, response.getId());
    }

    @Test
    void whenFindByIdThenReturnResourceNotFoundException() {
        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> pessoaService.findById(anyLong()));
    }

    private void startPessoa() {
        pessoa = new Pessoa(1L, "Test", "Departamento Teste", null);
    }
}