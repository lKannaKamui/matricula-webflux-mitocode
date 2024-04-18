package com.miacademia.service.impl;

import com.miacademia.model.Curso;
import com.miacademia.repository.GenericoRepository;
import com.miacademia.service.GenericoService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class GenericoServiceImpl<T,ID> implements GenericoService<T,ID>{

    protected abstract GenericoRepository<T, ID> getGenericoRepository();

    @Override
    public Mono<T> guardar(T t) {
        return getGenericoRepository().save(t);
    }

    @Override
    public Flux<T> listarTodos() {
        return getGenericoRepository().findAll();
    }

    @Override
    public Mono<T> buscar(ID id) {
        return getGenericoRepository().findById(id);
    }
}
