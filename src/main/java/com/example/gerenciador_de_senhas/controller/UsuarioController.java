package com.example.gerenciador_de_senhas.controller;


import com.example.gerenciador_de_senhas.model.Usuario;
import com.example.gerenciador_de_senhas.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    private final PasswordEncoder encoder;

    public UsuarioController(PasswordEncoder encoder, UsuarioService service) {
        this.encoder = encoder;
        this.service = service;
    }

    //processo de login
    @GetMapping("/validarSenha")
    public ResponseEntity<Boolean> validarSenha(@RequestParam String username, @RequestParam String senha) {


        Optional<Usuario> usuario = this.service.findByUsername(username);

        //verifica se encontrou o usuario
        if(usuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }

        boolean valid = false;

        //verifica se as senhas sao iguais
        Usuario user = usuario.get();
        valid = encoder.matches(senha, user.getPassword());

        int status = (valid) ? HttpStatus.OK.value() : HttpStatus.UNAUTHORIZED.value();

        return ResponseEntity.status(status).body(valid);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> insert(@RequestBody Usuario usuario) {
        try {
            usuario.setPassword(encoder.encode(usuario.getPassword()));
            this.service.saveUsuario(usuario);
            return ResponseEntity.ok().body("cadastrado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            usuario.setId(id);
            this.service.saveUsuario(usuario);
            return ResponseEntity.ok().body("atualizado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete (@PathVariable Long id) {
        try {
            this.service.delete(id);
            return ResponseEntity.ok().body("deletado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> findAll() {
        return ResponseEntity.ok().body(this.service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        if(this.service.findById(id) == null) {
            return ResponseEntity.badRequest().body("usuario nao encontrado");
        } else {
            return ResponseEntity.ok().body(this.service.findById(id));
        }
    }
}
