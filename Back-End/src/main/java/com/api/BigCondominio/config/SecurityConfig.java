package com.api.BigCondominio.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors() // habilita o suporte ao CorsConfig
            .and()
            .csrf().disable() // desativa CSRF para testes (cuidado em produção)
            .authorizeHttpRequests()
            .anyRequest().permitAll(); // libera todas as rotas sem autenticação

        return http.build();
    }
}
