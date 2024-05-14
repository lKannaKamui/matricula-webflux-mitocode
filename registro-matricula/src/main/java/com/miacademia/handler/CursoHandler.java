package com.miacademia.handler;

import com.miacademia.validator.RequestValidator;
import com.miacademia.dto.CursoDTO;
import com.miacademia.model.Curso;
import com.miacademia.service.CursoService;
import com.mongodb.internal.connection.Server;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
@RequiredArgsConstructor
public class CursoHandler {

    private final CursoService cursoService;
    @Qualifier("cursoMapper")
    private final ModelMapper modelMapper;

    private final RequestValidator validator;

    public Mono<ServerResponse> listar(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(cursoService.listar().map(this::convertirADTO), CursoDTO.class);

    }

    public Mono<ServerResponse> buscar(ServerRequest request){
        String id = request.pathVariable("id");

        return cursoService.buscar(id)
                .map(this::convertirADTO)
                .flatMap(e ->  ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(e)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    //@PreAuthorize("hasAuthority('ADMIN')")
    public Mono<ServerResponse> guardar(ServerRequest request){

        Mono<CursoDTO> cursoDTOMono = request.bodyToMono(CursoDTO.class);

        return cursoDTOMono
                .flatMap(validator::validate)
                .flatMap(e -> cursoService.guardar(this.convertirADocumento(e)))
                .map(this::convertirADTO)
                .flatMap(e -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(e)));
    }

    public Mono<ServerResponse> actualizar(ServerRequest request){
        String id = request.pathVariable("id");

        return request.bodyToMono(CursoDTO.class)
                .map(e -> {
                    e.setId(id);
                    return e;
                })
                .flatMap(validator::validate)
                .flatMap(e -> cursoService.actualizar(id, convertirADocumento(e)))
                .map(this::convertirADTO)
                .flatMap( e -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(e)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> eliminar(ServerRequest request){

        String id = request.pathVariable("id");

        return cursoService.eliminar(id)
                .flatMap(resultado ->
                {
                    if(resultado){
                        return ServerResponse.noContent().build();
                    }else{
                        return ServerResponse.notFound().build();
                    }
                });
    }

    private CursoDTO convertirADTO(Curso model){
        return modelMapper.map(model, CursoDTO.class);
    }

    private Curso convertirADocumento(CursoDTO dto){
        return modelMapper.map(dto, Curso.class);
    }
}
