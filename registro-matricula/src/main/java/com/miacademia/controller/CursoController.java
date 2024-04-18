package com.miacademia.controller;

import com.miacademia.model.Curso;
import com.miacademia.service.CursoService;
import com.miacademia.service.impl.CursoServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/cursos")
@RequiredArgsConstructor
public class CursoController {

    private final CursoService cursoService;

    @GetMapping
    public Mono<ResponseEntity<Flux<Curso>>> listar() {
        return Mono.just(ResponseEntity.ok()
                                       .contentType(MediaType.APPLICATION_JSON)
                                       .body(cursoService.listar()))
                   .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Curso>> buscar(@PathVariable String id) {
          return cursoService.buscar(id).map(
                  c -> ResponseEntity.ok()
                          .contentType(MediaType.APPLICATION_JSON)
                        .body(c)
          ).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Curso>> guardar(@RequestBody Curso curso){

        return cursoService.guardar(curso)
                .map(e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Curso>> update(@PathVariable String id,@RequestBody Curso curso){

        return Mono.just(curso)
                .map(c -> {
                    c.setId(id);
                    return c;
                })
                .flatMap(e -> cursoService.actualizar(id, curso))
                .map(e -> ResponseEntity
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    public Mono<ResponseEntity<Boolean>> eliminar(@PathVariable String id){
        return cursoService.eliminar(id)
                .map(e -> ResponseEntity
                        .ok()
                        .body(e))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
