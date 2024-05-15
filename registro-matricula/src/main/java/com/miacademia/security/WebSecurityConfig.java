package com.miacademia.security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

//Clase S7
@Configuration
@EnableWebFluxSecurity
//@EnableReactiveMethodSecurity //autorizacion por metodos
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final CustomAuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .authorizeExchange(req -> {
                    req.pathMatchers("/login").permitAll();
                    req.pathMatchers(HttpMethod.GET,"/api/*/cursos/**").hasAnyAuthority("ADMIN","USER")
                            .pathMatchers(HttpMethod.POST,"/api/*/cursos/**").hasAnyAuthority("ADMIN")
                            .pathMatchers(HttpMethod.DELETE,"/api/*/cursos/**").hasAnyAuthority("ADMIN")
                            .pathMatchers(HttpMethod.PUT,"/api/*/cursos/**").hasAnyAuthority("ADMIN")

                            .pathMatchers(HttpMethod.GET,"/api/*/estudiantes/**").hasAnyAuthority("ADMIN","USER")
                            .pathMatchers(HttpMethod.POST,"/api/*/estudiantes/**").hasAnyAuthority("ADMIN")
                            .pathMatchers(HttpMethod.DELETE,"/api/*/estudiantes/**").hasAnyAuthority("ADMIN")
                            .pathMatchers(HttpMethod.PUT,"/api/*/estudiantes/**").hasAnyAuthority("ADMIN")

                            .pathMatchers(HttpMethod.POST,"/api/*/matriculas/**").hasAnyAuthority("ADMIN")
                            .anyExchange().authenticated();
                })
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .httpBasic(Customizer.withDefaults())
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository)
                .build();
    }
}
