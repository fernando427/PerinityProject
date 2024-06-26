package com.fernando.PerinityProject.controller;

import com.fernando.PerinityProject.model.Pessoa;
import com.fernando.PerinityProject.model.dto.PessoaHorasGastasDTO;
import com.fernando.PerinityProject.model.dto.PessoaMediaHorasGastasDTO;
import com.fernando.PerinityProject.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    private final PessoaService pessoaService;

    @Autowired
    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @PostMapping()
    public ResponseEntity<Pessoa> adicionarPessoa(@RequestBody Pessoa pessoa) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaService.adicionarPessoa(pessoa));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> alterarPessoa(@PathVariable Long id, @RequestBody Pessoa pessoa) {
        return ResponseEntity.ok().body(pessoaService.alterarPessoa(pessoa, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Pessoa> deletarPessoa(@PathVariable Long id) {
        pessoaService.deletarPessoa(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<PessoaHorasGastasDTO>> listarPessoas() {
        return ResponseEntity.ok().body(pessoaService.listarPessoas());
    }

    @GetMapping("/gastos")
    public ResponseEntity<List<PessoaMediaHorasGastasDTO>> buscarPessoaMediaHora(@RequestParam String nome,
                                                                  @RequestParam String startDate,
                                                                  @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return ResponseEntity.ok().body(pessoaService.buscarPessoaNomePeriodo(nome, start, end));
    }

    @GetMapping("/todas")
    public ResponseEntity<List<Pessoa>> listarTodasPessoas() {
        return ResponseEntity.ok().body(pessoaService.listarTodasPessoas());
    }
}
