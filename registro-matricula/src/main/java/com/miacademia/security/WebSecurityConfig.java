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
@EnableReactiveMethodSecurity //autorizacion por metodos
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
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .httpBasic(Customizer.withDefaults())
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository)
                .authorizeExchange(req -> {
                    req.pathMatchers("/login").permitAll();
                    req.pathMatchers(HttpMethod.GET,"/api/(v1|v2)/cursos/**").hasAnyRole("ADMIN","USER");
                    req.pathMatchers(HttpMethod.POST,"/api/(v1|v2)/cursos/**").hasAnyRole("ADMIN");
                    req.pathMatchers(HttpMethod.DELETE,"/api/(v1|v2)/cursos/**").hasAnyRole("ADMIN");
                    req.pathMatchers(HttpMethod.PUT,"/api/(v1|v2)/cursos/**").hasAnyRole("ADMIN");
                    req.pathMatchers(HttpMethod.GET,"/api/(v1|v2)/estudiantes/**").hasAnyRole("ADMIN","USER");
                    req.pathMatchers(HttpMethod.POST,"/api/(v1|v2)/estudiantes/**").hasAnyRole("ADMIN");
                    req.pathMatchers(HttpMethod.DELETE,"/api/(v1|v2)/estudiantes/**").hasAnyRole("ADMIN");
                    req.pathMatchers(HttpMethod.PUT,"/api/(v1|v2)/estudiantes/**").hasAnyRole("ADMIN");
                    req.pathMatchers(HttpMethod.GET,"/api/(v1|v2)/matriculas/**").hasAnyRole("ADMIN");
                    req.anyExchange().authenticated();
                })
                .build();
    }
}
