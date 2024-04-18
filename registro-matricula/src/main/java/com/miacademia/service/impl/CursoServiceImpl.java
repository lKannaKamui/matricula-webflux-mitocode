package com.miacademia.service.impl;

import com.miacademia.model.Curso;
import com.miacademia.repository.CursoRepository;
import com.miacademia.repository.GenericoRepository;
import com.miacademia.service.CursoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CursoServiceImpl extends GenericoServiceImpl<Curso,String> implements CursoService{

    private final CursoRepository cursoRepository;

    @Override
    protected GenericoRepository<Curso,String> getGenericoRepository() {
        return cursoRepository;
    }



}
