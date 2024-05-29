package com.fernando.PerinityProject.repositories;

import com.fernando.PerinityProject.model.Pessoa;
import com.fernando.PerinityProject.model.Tarefa;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    @Query("SELECT t.departamento, COUNT(t) FROM Tarefa t GROUP BY t.departamento")
    List<Object[]> countTarefasPorDepartamento();

    List<Tarefa> findByPessoa(Pessoa pessoa);

    @Query("SELECT t FROM Tarefa t WHERE t.pessoa IS NULL AND t.status = 'PENDENTE' ORDER BY t.prazo ASC")
    List<Tarefa> findTarefasPendentes(Pageable pageable);

    @Query("SELECT t FROM Tarefa t WHERE t.pessoa.nome = :nome AND t.prazo BETWEEN :startDate AND :endDate")
    List<Tarefa> findByNomeAndPeriodo(@Param("nome") String nome, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
