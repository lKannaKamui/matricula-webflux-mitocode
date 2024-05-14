package com.miacademia.service;

import com.miacademia.model.Curso;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GenericoService<T,ID>{

    Flux<T> listar();
    Mono<T> guardar(T t);
    Mono<T> buscar(ID id);
    Mono<T> actualizar(ID id, T t);
    Mono<Boolean> eliminar(ID id);
}
