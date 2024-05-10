package com.miacademia.config;

import com.miacademia.handler.CursoHandler;
import com.miacademia.handler.EstudianteHandler;
import com.miacademia.handler.MatriculaHandler;
import com.mongodb.internal.connection.Server;
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
        return route(GET("/api/v2/cursos"), handler::listar)
                .andRoute(GET("/api/v2/cursos/{id}"), handler::buscar)
                .andRoute(POST("/api/v2/cursos"), handler::guardar)
                .andRoute(PUT("/api/v2/cursos/{id}"), handler::actualizar)
                .andRoute(DELETE("/api/v2/cursos/{id}"), handler::eliminar);
    }

    @Bean
    public RouterFunction<ServerResponse> rutasEstudiante(EstudianteHandler handler){
        return route(GET("/api/v2/estudiantes"), handler::listar)
                .andRoute(GET("/api/v2/estudiantes/{id}"), handler::buscar)
                .andRoute(POST("/api/v2/estudiantes"), handler::guardar)
                .andRoute(PUT("/api/v2/estudiantes/{id}"), handler::actualizar)
                .andRoute(DELETE("/api/v2/estudiantes/{id}"), handler::eliminar);
    }

    @Bean
    public RouterFunction<ServerResponse> rutasMatricula(MatriculaHandler handler){
        return route(POST("/api/v2/matriculas"), handler::registrar);
    }
}
