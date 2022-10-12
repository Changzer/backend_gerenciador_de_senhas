package com.example.gerenciador_de_senhas.controller;



import com.example.gerenciador_de_senhas.model.Senha;
import com.example.gerenciador_de_senhas.service.SenhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@CrossOrigin
@Controller
@RequestMapping("/api/senha")
public class SenhaController {

    @Autowired
    private SenhaService service;


    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Senha senha) {
        System.out.println("encontrou o endpoint");
        try {
            this.service.insert(senha);
            return ResponseEntity.ok().body("cadastrado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            this.service.delete(id);
            return ResponseEntity.ok().body("cadastrado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error: " + e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Senha senha) {
        try {
            senha.setId(id);
            this.service.insert(senha);
            return ResponseEntity.ok().body("cadastrado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Senha>> findAll() {
        return ResponseEntity.ok().body(this.service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        if (this.service.findById(id) == null) {
            return ResponseEntity.badRequest().body("senha não encontrada");
        } else {
            return ResponseEntity.ok().body(this.service.findById(id));
        }
    }
}
