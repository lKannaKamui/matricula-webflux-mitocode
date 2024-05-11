package com.miacademia.repository;

import com.miacademia.model.Usuario;
import reactor.core.publisher.Mono;

public interface UsuarioRepository extends GenericoRepository<Usuario, String> {

    //query methods or derived queries
    Mono<Usuario> findOneByUsername(String username);
}
