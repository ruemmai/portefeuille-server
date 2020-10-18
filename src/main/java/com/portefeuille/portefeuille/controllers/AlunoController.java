package com.portefeuille.portefeuille.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.portefeuille.portefeuille.models.entities.Aluno;
import com.portefeuille.portefeuille.services.AlunoService;

import org.aspectj.apache.bcel.classfile.Module.Require;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class AlunoController {

  @Autowired
  AlunoService service;

  @GetMapping("/alunos")
  public ResponseEntity getAllAlunos(@RequestParam(value = "nome", required = false) String nome,
      @RequestParam(value = "sobrenome", required = false) String sobrenome) {
    List<Aluno> alunos;

    System.out.println(nome + " " + sobrenome);

    try {
      alunos = service.obterAlunos();
      if (nome != null) {
        alunos = alunos.stream().filter(aluno -> aluno.getNome().contains(nome)).collect(Collectors.toList());
      }
      if (sobrenome != null) {
        alunos = alunos.stream().filter(aluno -> aluno.getNome().contains(sobrenome)).collect(Collectors.toList());
      }
      return new ResponseEntity<>(alunos, HttpStatus.OK);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/alunos/{matricula}")
  public ResponseEntity getAluno(@PathVariable Long matricula) {
    Optional<Aluno> aluno;
    try {
      aluno = service.obterAluno(matricula);
      if (aluno.isPresent()) {
        return new ResponseEntity<>(aluno, HttpStatus.OK);
      } else {
        throw new Exception();
      }
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }

  }

}
