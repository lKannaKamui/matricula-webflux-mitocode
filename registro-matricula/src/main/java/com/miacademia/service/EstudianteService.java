package com.miacademia.service;

import com.miacademia.model.Estudiante;
import reactor.core.publisher.Flux;

public interface EstudianteService extends GenericoService<Estudiante,String> {

    public Flux<Estudiante> listarPorOrden(String orden);
}
