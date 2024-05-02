package com.miacademia.controller;

import com.miacademia.dto.MatriculaDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/matriculas")
public class MatriculaController {

    @PostMapping
    public Mono<ResponseEntity<MatriculaDTO>> registrar(@Valid @RequestBody MatriculaDTO matriculaDTO){
        return null;
    }
}
