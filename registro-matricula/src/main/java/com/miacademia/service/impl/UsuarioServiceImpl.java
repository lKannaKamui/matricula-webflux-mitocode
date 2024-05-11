package com.miacademia.service.impl;

import com.miacademia.model.Rol;
import com.miacademia.model.Usuario;
import com.miacademia.repository.GenericoRepository;
import com.miacademia.repository.RolRepository;
import com.miacademia.repository.UsuarioRepository;
import com.miacademia.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl extends GenericoServiceImpl<Usuario, String> implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final BCryptPasswordEncoder bCrypt;

    @Override
    public Mono<Usuario> saveHash(Usuario usuario) {
        usuario.setPassword(bCrypt.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    @Override
    public Mono<com.miacademia.security.Usuario> searchByUser(String usuario) {
        return usuarioRepository.findOneByUsername(usuario)
                .flatMap(u -> Flux.fromIterable(u.getRoles())
                        .flatMap(userRole -> rolRepository.findById(userRole.getId())
                                .map(Rol::getName))
                        .collectList()
                        .map(roles -> new com.miacademia.security.Usuario(u.getUsername(), u.getPassword(), u.isStatus(), roles))
                );
    }

    @Override
    protected GenericoRepository<Usuario, String> getGenericoRepository() {
        return usuarioRepository;
    }
}
