package com.miacademia.controller;

import com.miacademia.dto.MatriculaDTO;
import com.miacademia.model.Matricula;
import com.miacademia.service.MatriculaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/matriculas")
@RequiredArgsConstructor
public class MatriculaController {

    private final MatriculaService matriculaService;

    @Qualifier("matriculaMapper")
    private final ModelMapper modelMapper;

    @PostMapping
    public Mono<ResponseEntity<MatriculaDTO>> registrar(@Valid @RequestBody MatriculaDTO matriculaDTO){
        return matriculaService.guardar(convertirADocumento(matriculaDTO))
                .map(this::convertirADTO)
                .map(e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    private MatriculaDTO convertirADTO(Matricula model){
        return modelMapper.map(model, MatriculaDTO.class);
    }

    private Matricula convertirADocumento(MatriculaDTO dto){
        return modelMapper.map(dto, Matricula.class);
    }

}
