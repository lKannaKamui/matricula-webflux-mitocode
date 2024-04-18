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
                                       .body(cursoService.listarTodos()))
                   .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
//    public Mono<ResponseEntity<Mono<Curso>>> buscar(@PathVariable String id) {
    public Mono<ResponseEntity<Curso>> buscar(@PathVariable String id) {
/*        return Mono.just(ResponseEntity.ok()
                                       .contentType(MediaType.APPLICATION_JSON)
                                       .body(cursoService.buscar(id)))
                   .defaultIfEmpty(ResponseEntity.notFound().build());*/
          return cursoService.buscar(id).map(
                  c -> ResponseEntity.ok()
                          .contentType(MediaType.APPLICATION_JSON)
                        .body(c)
          ).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<Curso> guardar(@RequestBody Curso curso){
        return cursoService.guardar(curso);
    }
}
