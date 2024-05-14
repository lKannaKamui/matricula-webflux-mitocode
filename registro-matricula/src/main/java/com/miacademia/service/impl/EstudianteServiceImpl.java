package com.miacademia.service.impl;

import com.miacademia.model.Estudiante;
import com.miacademia.repository.EstudianteRepository;
import com.miacademia.repository.GenericoRepository;
import com.miacademia.service.EstudianteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class EstudianteServiceImpl extends GenericoServiceImpl<Estudiante,String> implements EstudianteService {

    private final EstudianteRepository estudianteRepository;

    @Override
    protected GenericoRepository<Estudiante, String> getGenericoRepository() {
        return estudianteRepository;
    }

    @Override
    public Flux<Estudiante> listarPorOrden(String orden) {
        if("desc".equalsIgnoreCase(orden)){
            return estudianteRepository.findAll(Sort.by(Sort.Order.desc("edad")));
        }

        return estudianteRepository.findAll(Sort.by(Sort.Order.asc("edad")));
    }
}
