package com.example.gerenciador_de_senhas.service;

import com.example.gerenciador_de_senhas.model.Senha;
import com.example.gerenciador_de_senhas.repository.SenhaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class SenhaService {

    @Autowired
    private SenhaRepository repository;

    @Transactional
    public void insert(Senha senha) {
        this.repository.save(senha);
    }

    @Transactional
    public void delete(Long id) {
        this.repository.deleteById(id);
    }

    @Transactional
    public List<Senha> findAll() {
        return this.repository.findAll();
    }

    @Transactional
    public Senha findById(Long id) {
        Senha senha = new Senha();
        if(this.repository.findById(id).isPresent()) {
            senha = this.repository.findById(id).get();
            return senha;
        } else {
            return null;
        }
    }
}
