package com.fernando.PerinityProject.repositories;

import com.fernando.PerinityProject.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    @Query("SELECT p.departamento, COUNT(p) FROM Pessoa p GROUP BY p.departamento")
    List<Object[]> countPessoasPorDepartamento();
}
