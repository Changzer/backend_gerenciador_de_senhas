package com.example.gerenciador_de_senhas.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(schema = "public", name = "senhas")
@NoArgsConstructor
@Getter
@Setter
public class Senha extends AbstractEntity {
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "url")
    private String url;
    @Column(name = "password")
    private String password;

}
