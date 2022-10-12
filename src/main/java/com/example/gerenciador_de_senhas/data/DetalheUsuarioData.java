package com.example.gerenciador_de_senhas.data;

import com.example.gerenciador_de_senhas.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class DetalheUsuarioData implements UserDetails {

    private final Optional<Usuario> usuario;

    public DetalheUsuarioData(Optional<Usuario> usuario) {
        this.usuario = usuario;
    }

    @Override //altorizacaos do usuario
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override //retorna a senha do usuario ou uma senha nula
    public String getPassword() {
        return usuario.orElse(new Usuario()).getPassword();
    }

    @Override //retorna o username do usuario ou um username nulo
    public String getUsername() {
        return usuario.orElse(new Usuario()).getUsername();
    }

    //possivel melhoria: controlar essas variaveis (se esta expirado, bloqueado, etc
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
