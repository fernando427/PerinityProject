package com.fernando.PerinityProject.repositories;

import com.fernando.PerinityProject.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    @Query("SELECT t.departamento, COUNT(t) FROM Tarefa t GROUP BY t.departamento")
    List<Object[]> countTarefasPorDepartamento();
}
