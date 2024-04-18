package com.miacademia.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.NoRepositoryBean;
import reactor.core.publisher.Mono;

@NoRepositoryBean
public interface GenericoRepository<T,ID> extends ReactiveMongoRepository<T,ID> {

}
