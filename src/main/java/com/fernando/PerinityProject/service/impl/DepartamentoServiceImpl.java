package com.fernando.PerinityProject.service.impl;

import com.fernando.PerinityProject.model.dto.DepartamentoDTO;
import com.fernando.PerinityProject.repositories.PessoaRepository;
import com.fernando.PerinityProject.repositories.TarefaRepository;
import com.fernando.PerinityProject.service.DepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class DepartamentoServiceImpl implements DepartamentoService {

    private final PessoaRepository pessoaRepository;
    private final TarefaRepository tarefaRepository;

    @Autowired
    public DepartamentoServiceImpl(PessoaRepository pessoaRepository,
                             TarefaRepository tarefaRepository) {
        this.pessoaRepository = pessoaRepository;
        this.tarefaRepository = tarefaRepository;
    }

    @Override
    public List<DepartamentoDTO> obterDep() {
        List<Object[]> resultadosPessoas = pessoaRepository.countPessoasPorDepartamento();
        List<Object[]> resultadosTarefas = tarefaRepository.countTarefasPorDepartamento();

        Map<String, DepartamentoDTO> departamentosMap = new HashMap<>();

        for (Object[] resultado : resultadosPessoas) {
            String departamento = (String) resultado[0];
            Long quantidadePessoas = (Long) resultado[1];

            DepartamentoDTO dto = departamentosMap.getOrDefault(departamento, new DepartamentoDTO(departamento, 0L, 0L));
            dto.setQntPessoas(quantidadePessoas);
            departamentosMap.put(departamento, dto);
        }

        for (Object[] resultado : resultadosTarefas) {
            String departamento = (String) resultado[0];
            Long quantidadeTarefas = (Long) resultado[1];

            DepartamentoDTO dto = departamentosMap.getOrDefault(departamento, new DepartamentoDTO(departamento, 0L, 0L));
            dto.setQntTarefas(quantidadeTarefas);
            departamentosMap.put(departamento, dto);
        }

        return new ArrayList<>(departamentosMap.values());
    }
}
