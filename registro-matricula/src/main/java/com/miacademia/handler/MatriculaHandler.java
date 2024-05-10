package com.miacademia.handler;

import com.miacademia.dto.MatriculaDTO;
import com.miacademia.model.Matricula;
import com.miacademia.service.MatriculaService;
import com.miacademia.validator.RequestValidator;
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
public class MatriculaHandler {

    private final MatriculaService matriculaService;

    @Qualifier("matriculaMapper")
    private final ModelMapper modelMapper;

    private final RequestValidator validator;

    public Mono<ServerResponse> registrar(ServerRequest request) {

        Mono<MatriculaDTO> matricula = request.bodyToMono(MatriculaDTO.class);

        return matricula
                .flatMap(validator::validate)
                .flatMap(e -> matriculaService.guardar(this.convertirADocumento(e)))
                .map(this::convertirADTO)
                .flatMap(e -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(e)));
    }

    private MatriculaDTO convertirADTO(Matricula model){
        return modelMapper.map(model, MatriculaDTO.class);
    }

    private Matricula convertirADocumento(MatriculaDTO dto){
        return modelMapper.map(dto, Matricula.class);
    }

}
