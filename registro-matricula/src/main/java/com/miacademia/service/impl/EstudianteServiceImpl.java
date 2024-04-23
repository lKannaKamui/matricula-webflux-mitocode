package com.miacademia.service.impl;

import com.miacademia.model.Estudiante;
import com.miacademia.repository.EstudianteRepository;
import com.miacademia.repository.GenericoRepository;
import com.miacademia.service.EstudianteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EstudianteServiceImpl extends GenericoServiceImpl<Estudiante,String> implements EstudianteService {

    private final EstudianteRepository estudianteRepository;

    @Override
    protected GenericoRepository<Estudiante, String> getGenericoRepository() {
        return estudianteRepository;
    }
}
