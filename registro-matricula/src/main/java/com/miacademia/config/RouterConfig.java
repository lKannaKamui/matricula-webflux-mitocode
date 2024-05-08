package com.miacademia.config;

import com.miacademia.handler.CursoHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> rutasCurso(CursoHandler handler){
        return route(GET("/v2/cursos"), handler::listar)
                .andRoute(GET("/v2/cursos/{id}"), handler::buscar)
                .andRoute(POST("/v2/cursos"), handler::guardar)
                .andRoute(PUT("/v2/cursos/{id}"), handler::actualizar)
                .andRoute(DELETE("/v2/cursos/{id}"), handler::eliminar);
    }

}
