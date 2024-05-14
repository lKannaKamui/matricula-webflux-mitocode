package com.miacademia.service.impl;

import com.miacademia.model.Curso;
import com.miacademia.repository.GenericoRepository;
import com.miacademia.service.GenericoService;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class GenericoServiceImpl<T,ID> implements GenericoService<T,ID>{

    protected abstract GenericoRepository<T, ID> getGenericoRepository();

    @Override
    public Mono<T> guardar(T t) {
        return getGenericoRepository().save(t);
    }

    @Override
    public Flux<T> listar() {
        return getGenericoRepository().findAll();
    }

    @Override
    public Mono<T> buscar(ID id) {
        return getGenericoRepository().findById(id);
    }

    @Override
    public Mono<T> actualizar(ID id, T t) {
        return getGenericoRepository().findById(id).flatMap(e -> getGenericoRepository().save(t));
    }

    @Override
    public Mono<Boolean> eliminar(ID id) {
        return getGenericoRepository().findById(id).hasElement()
                .flatMap(result ->{
                            if(result){
                                return getGenericoRepository().deleteById(id).thenReturn(true);
                            }else{
                                return Mono.just(false);
                            }
                });
    }

}
