package com.example.gerenciador_de_senhas.repository;

import com.example.gerenciador_de_senhas.model.Senha;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SenhaRepository extends JpaRepository<Senha, Long> {
}
