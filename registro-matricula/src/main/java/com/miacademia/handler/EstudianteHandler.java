package com.miacademia.handler;

import com.miacademia.dto.CursoDTO;
import com.miacademia.dto.EstudianteDTO;
import com.miacademia.model.Curso;
import com.miacademia.model.Estudiante;
import com.miacademia.service.EstudianteService;
import com.miacademia.validator.RequestValidator;
import com.mongodb.internal.connection.Server;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
@RequiredArgsConstructor
public class EstudianteHandler {

    private final EstudianteService estudianteService;

    @Qualifier("estudianteMapper")
    private final ModelMapper modelMapper;

    private final RequestValidator validator;

    public Mono<ServerResponse> listar(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(estudianteService.listar().map(this::convertirADTO), EstudianteDTO.class);
    }

    public Mono<ServerResponse> listarPorOrden(ServerRequest request) {

        String orden = request.queryParam("orderBy").orElse("");

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(estudianteService.listarPorOrden(orden).map(this::convertirADTO), EstudianteDTO.class);
    }

    public Mono<ServerResponse> buscar(ServerRequest request) {
        String id = request.pathVariable("id");

        return estudianteService.buscar(id)
                .map(this::convertirADTO)
                .flatMap(e -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue((e))))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> guardar(ServerRequest request) {
        Mono<EstudianteDTO> estudianteDTO = request.bodyToMono(EstudianteDTO.class);

        return estudianteDTO
                .flatMap(validator::validate)
                .flatMap(e -> estudianteService.guardar(this.convertirADocumento(e)))
                .map(this::convertirADTO)
                .flatMap(e -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(e)));
    }

    public Mono<ServerResponse> actualizar(ServerRequest request) {
        String id = request.pathVariable("id");

        return request.bodyToMono(EstudianteDTO.class)
                .map(e -> {
                    e.setId(id);
                    return e;
                })
                .flatMap(validator::validate)
                .flatMap(e -> estudianteService.actualizar(id, convertirADocumento(e)))
                .map(this::convertirADTO)
                .flatMap(e -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(e)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> eliminar(ServerRequest request) {
        String id = request.pathVariable("id");

        return estudianteService.eliminar(id)
                .flatMap(resultado -> {
                    if(resultado){
                        return ServerResponse.noContent().build();
                    }else{
                        return ServerResponse.notFound().build();
                    }
                });
    }

    private EstudianteDTO convertirADTO(Estudiante model){
        return modelMapper.map(model, EstudianteDTO.class);
    }

    private Estudiante convertirADocumento(EstudianteDTO dto){
        return modelMapper.map(dto, Estudiante.class);
    }
}

