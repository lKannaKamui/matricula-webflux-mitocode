package com.miacademia.service.impl;

import com.miacademia.model.Matricula;
import com.miacademia.repository.GenericoRepository;
import com.miacademia.repository.MatriculaRepository;
import com.miacademia.service.MatriculaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatriculaServiceImpl extends GenericoServiceImpl<Matricula,String> implements MatriculaService {

    private final MatriculaRepository matriculaRepository;

    @Override
    protected GenericoRepository<Matricula, String> getGenericoRepository() {
        return matriculaRepository;
    }

 }
