package com.miacademia.service;

import com.miacademia.model.Curso;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GenericoService<T,ID>{

    Flux<T> listarTodos();
    Mono<T> guardar(T t);
    Mono<T> buscar(ID id);

}
