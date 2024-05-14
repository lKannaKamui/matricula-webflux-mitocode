package com.miacademia.repository;

import com.miacademia.model.Estudiante;
import org.springframework.data.mongodb.repository.Query;
import reactor.core.publisher.Flux;

public interface EstudianteRepository extends GenericoRepository<Estudiante, String> {

}
