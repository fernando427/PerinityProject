package com.fernando.PerinityProject.service.impl;

import com.fernando.PerinityProject.model.dto.DepartamentoDTO;
import com.fernando.PerinityProject.repositories.PessoaRepository;
import com.fernando.PerinityProject.repositories.TarefaRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
class DepartamentoServiceImplTest {

    @InjectMocks
    private DepartamentoServiceImpl departamentoService;

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private TarefaRepository tarefaRepository;

    @Test
    void whenListDepartamentosReturnDepartamentosEQntPessoasETarefas() {
        List<Object[]> resultadosPessoas = Arrays.asList(
                new Object[]{"TI", 2L},
                new Object[]{"Segurança", 1L}
        );
        List<Object[]> resultadosTarefas = Arrays.asList(
                new Object[]{"TI", 2L},
                new Object[]{"Segurança", 1L}
        );
        when(pessoaRepository.countPessoasPorDepartamento()).thenReturn(resultadosPessoas);
        when(tarefaRepository.countTarefasPorDepartamento()).thenReturn(resultadosTarefas);

        List<DepartamentoDTO> resultado = departamentoService.obterDep();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        DepartamentoDTO depA = resultado.stream()
                .filter(dto -> dto.getNome().equals("TI"))
                .findFirst()
                .orElse(null);
        assertNotNull(depA);
        assertEquals(2L, depA.getQntPessoas());
        assertEquals(2L, depA.getQntTarefas());

        DepartamentoDTO depB = resultado.stream()
                .filter(dto -> dto.getNome().equals("Segurança"))
                .findFirst()
                .orElse(null);
        assertNotNull(depA);
        assertEquals(1L, depB.getQntPessoas());
        assertEquals(1L, depB.getQntTarefas());

    }
}