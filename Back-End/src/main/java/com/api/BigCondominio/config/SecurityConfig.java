package com.api.BigCondominio.config;
import org.springframework.security.core.userdetails.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors()
            .and()
            .csrf().disable()
            .authorizeHttpRequests(auth -> auth
                .anyRequest().authenticated() // exige autenticação para todas as rotas
            )
            .httpBasic(); // autenticação básica (usuário/senha via header)

        return http.build();
    }

    @Bean
    public UserDetailsService users() {
        UserDetails user = User.builder()
            .username("admin")
            .password("{noop}123456") // {noop} = senha sem criptografia
            .roles("USER")
            .build();

        return new InMemoryUserDetailsManager(user);
    }
}
