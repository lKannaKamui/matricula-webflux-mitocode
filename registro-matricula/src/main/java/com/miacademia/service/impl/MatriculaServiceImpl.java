package com.miacademia.service.impl;

import com.miacademia.exception.LogicaException;
import com.miacademia.model.Matricula;
import com.miacademia.repository.GenericoRepository;
import com.miacademia.repository.MatriculaRepository;
import com.miacademia.service.MatriculaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MatriculaServiceImpl extends GenericoServiceImpl<Matricula,String> implements MatriculaService {

    private final MatriculaRepository matriculaRepository;

    @Override
    protected GenericoRepository<Matricula, String> getGenericoRepository() {
        return matriculaRepository;
    }

    @Override
    public Mono<Matricula> guardar(Matricula matricula) {

        if(matricula.getFecha().toLocalDate().isBefore(LocalDate.now())){
            throw new LogicaException("La fecha de matricula no puede ser anterior al dia de hoy");
        }

        return super.guardar(matricula);
    }
}
