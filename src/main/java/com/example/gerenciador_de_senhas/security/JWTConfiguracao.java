package com.example.gerenciador_de_senhas.security;

import com.example.gerenciador_de_senhas.service.DetalheUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.net.http.HttpHeaders;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
//diz para usar as minhas classes como configuracao
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class JWTConfiguracao extends WebSecurityConfigurerAdapter {

    private static final String[] AUTH_WHITELIST = {
            "/api/**",
            "/api/login**",
            "/api/usuarios/cadastrar/**"
    };
    private DetalheUsuarioService detalheUsuarioService;
    private PasswordEncoder passwordEncoder;

    public JWTConfiguracao(DetalheUsuarioService detalheUsuarioService, PasswordEncoder passwordEncoder) {
        this.detalheUsuarioService = detalheUsuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(detalheUsuarioService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       //descricao: desabilita o csrf, autoriza o acesso de qualquer um para o /login, expeficia que todos os outros
        //endpoins precisao de autorizacao e diz quais sao os filtros para paazer a autenticacao e validacao
        String[] urls = {"/login/**", "/api/usuarios/cadastrar/**"};

        http.cors().and().csrf().disable().authorizeRequests();
        http.authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll();
        http.authorizeRequests().antMatchers(GET,"/**").hasRole("ADMIN");
        http.authorizeRequests().antMatchers(POST, "/**").hasRole("ADMIN");
        http.authorizeRequests().anyRequest().authenticated();
        http.httpBasic();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

                http.addFilter(new JWTAutenticarFilter(authenticationManager()));
                http.addFilter(new JWTValidarFilter(authenticationManager()));
    }







}
