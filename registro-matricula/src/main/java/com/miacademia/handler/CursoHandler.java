package com.miacademia.handler;

import com.miacademia.config.validator.RequestValidator;
import com.miacademia.dto.CursoDTO;
import com.miacademia.model.Curso;
import com.miacademia.service.CursoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CursoHandler {

    private final CursoService cursoService;
    @Qualifier("cursoMapper")
    private final ModelMapper modelMapper;

    private final RequestValidator requestValidator;

    public Mono<ServerResponse> listar(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(cursoService.listar().map(this::convertirADTO), CursoDTO.class);

    }

    private CursoDTO convertirADTO(Curso model){
        return modelMapper.map(model, CursoDTO.class);
    }

    private Curso convertirADocumento(CursoDTO dto){
        return modelMapper.map(dto, Curso.class);
    }
}
