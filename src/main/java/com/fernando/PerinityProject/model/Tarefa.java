package com.fernando.PerinityProject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tarefas")
public class Tarefa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descricao;
    private LocalDate prazo;
    private Integer duracaoH;
    private String status;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "departamento_id")
    private Departamento departamento;

    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;
}
