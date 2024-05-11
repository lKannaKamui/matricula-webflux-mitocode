package com.miacademia.service;

import com.miacademia.model.Usuario;
import reactor.core.publisher.Mono;

public interface UsuarioService extends GenericoService<Usuario, String> {

    Mono<Usuario> saveHash(Usuario user);
    Mono<com.miacademia.security.Usuario> searchByUser(String username);
}
