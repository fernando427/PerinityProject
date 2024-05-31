package com.fernando.PerinityProject.controller;

import com.fernando.PerinityProject.model.Pessoa;
import com.fernando.PerinityProject.model.dto.PessoaHorasGastasDTO;
import com.fernando.PerinityProject.model.dto.PessoaMediaHorasGastasDTO;
import com.fernando.PerinityProject.service.PessoaService;
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
import static org.mockito.Mockito.*;

@SpringBootTest
class PessoaControllerTest {

    @InjectMocks
    private PessoaController pessoaController;

    @Mock
    private PessoaService pessoaService;

    private Pessoa pessoa;
    private Pessoa pessoa2;
    private List<Pessoa> pessoas;
    private PessoaHorasGastasDTO pessoaHGDTO;
    private PessoaHorasGastasDTO pessoaHGDTO2;
    private List<PessoaHorasGastasDTO> pessoasHGDTO;
    private PessoaMediaHorasGastasDTO pessoaMHGDTO;
    private PessoaMediaHorasGastasDTO pessoaMHGDTO2;
    private List<PessoaMediaHorasGastasDTO> pessoasMHGDTO;

    @BeforeEach
    void setUp() {
        startPessoa();
    }

    @Test
    void whenAdicionarPessoaThenReturnAnPessoa() {
        when(pessoaService.adicionarPessoa(any(Pessoa.class))).thenReturn(pessoa);

        ResponseEntity<Pessoa> resultado = pessoaController.adicionarPessoa(pessoa);

        assertNotNull(resultado);
        assertNotNull(resultado.getBody());
        assertEquals(HttpStatus.CREATED, resultado.getStatusCode());
        assertEquals(ResponseEntity.class, resultado.getClass());
        assertEquals(Pessoa.class, resultado.getBody().getClass());
    }

    @Test
    void whenAlterarPessoaThenReturnPessoaAlterada() {
        when(pessoaService.alterarPessoa(any(Pessoa.class), anyLong())).thenReturn(pessoa);

        ResponseEntity<Pessoa> resultado = pessoaController.alterarPessoa(1L, pessoa);

        assertNotNull(resultado);
        assertNotNull(resultado.getBody());
        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(ResponseEntity.class, resultado.getClass());
        assertEquals(1L, resultado.getBody().getId());
        assertEquals(Pessoa.class, resultado.getBody().getClass());
    }

    @Test
    void whenDeletarPessoaThenReturnNoContent() {
        doNothing().when(pessoaService).deletarPessoa(anyLong());

        ResponseEntity<Pessoa> resultado = pessoaController.deletarPessoa(1L);

        assertNotNull(resultado);
        assertEquals(HttpStatus.NO_CONTENT, resultado.getStatusCode());
        assertEquals(ResponseEntity.class, resultado.getClass());
        verify(pessoaService, times(1)).deletarPessoa(anyLong());
    }

    @Test
    void whenListarPessoasThenReturnAnListOfPessoasHorasGastasDTO() {
        when(pessoaService.listarPessoas()).thenReturn(pessoasHGDTO);

        ResponseEntity<List<PessoaHorasGastasDTO>> resultado = pessoaController.listarPessoas();

        assertNotNull(resultado);
        assertNotNull(resultado.getBody());
        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(ResponseEntity.class, resultado.getClass());
        assertEquals(PessoaHorasGastasDTO.class, resultado.getBody().get(0).getClass());
    }

    @Test
    void whenBuscarPessoaMediaHoraThenReturnListOfPessoaMediaHorasGastasDTO() {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);
        when(pessoaService.buscarPessoaNomePeriodo(anyString(), any(LocalDate.class), any(LocalDate.class))).thenReturn(pessoasMHGDTO);

        ResponseEntity<List<PessoaMediaHorasGastasDTO>> resultado = pessoaController.buscarPessoaMediaHora("Teste", "2024-01-01", "2024-12-31");

        assertNotNull(resultado);
        assertNotNull(resultado.getBody());
        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(ResponseEntity.class, resultado.getClass());
        assertEquals(PessoaMediaHorasGastasDTO.class, resultado.getBody().get(0).getClass());
    }

    @Test
    void whenListarTodasPessoasThenReturnListOfTodasPessoas() {
        when(pessoaService.listarTodasPessoas()).thenReturn(pessoas);

        ResponseEntity<List<Pessoa>> resultado = pessoaController.listarTodasPessoas();

        assertNotNull(resultado);
        assertNotNull(resultado.getBody());
        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(ResponseEntity.class, resultado.getClass());
        assertEquals(Pessoa.class, resultado.getBody().get(0).getClass());
    }

    private void startPessoa() {
        pessoa = new Pessoa(1L, "Tomas", "Departamento Teste", null);
        pessoa2 = new Pessoa(2L, "Agostinho", "Departamento Teste 2", null);
        pessoas = List.of(pessoa, pessoa2);

        pessoaHGDTO = new PessoaHorasGastasDTO("Teste 1", "TI", 5);
        pessoaHGDTO2 = new PessoaHorasGastasDTO("Teste 2", "Segurança",7);
        pessoasHGDTO = List.of(pessoaHGDTO, pessoaHGDTO2);

        pessoaMHGDTO = new PessoaMediaHorasGastasDTO("Teste", "2024-01-01 to 2024-12-31", 7.5);
        pessoaMHGDTO2 = new PessoaMediaHorasGastasDTO("Teste", "2023-01-01 to 2023-12-31", 5.0);
        pessoasMHGDTO = List.of(pessoaMHGDTO, pessoaMHGDTO2);
    }
}