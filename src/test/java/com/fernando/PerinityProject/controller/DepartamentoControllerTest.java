package com.fernando.PerinityProject.controller;

import com.fernando.PerinityProject.model.dto.DepartamentoDTO;
import com.fernando.PerinityProject.service.DepartamentoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
class DepartamentoControllerTest {

    @InjectMocks
    private DepartamentoController departamentoController;

    @Mock
    private DepartamentoService departamentoService;

    private DepartamentoDTO departamentoDTO;
    private DepartamentoDTO departamentoDTO2;
    private List<DepartamentoDTO> departamentoDTOS;

    @BeforeEach
    void setUp() {
        startDepartamento();
    }

    @Test
    void whenListarDepartamentosThenReturnAnListOfDepartamentos() {
        when(departamentoService.obterDep()).thenReturn(departamentoDTOS);

        ResponseEntity<List<DepartamentoDTO>> resultado = departamentoController.listarDepartamentos();

        assertNotNull(resultado);
        assertNotNull(resultado.getBody());
        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(ResponseEntity.class, resultado.getClass());
        assertEquals(DepartamentoDTO.class, resultado.getBody().get(0).getClass());
    }

    private void startDepartamento() {
        departamentoDTO = new DepartamentoDTO("Teste 1", 1L, 1L);
        departamentoDTO2 = new DepartamentoDTO("Teste 2", 1L, 1L);
        departamentoDTOS = List.of(departamentoDTO, departamentoDTO2);
    }
}