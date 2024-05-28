package com.fernando.PerinityProject.repositories;

import com.fernando.PerinityProject.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
