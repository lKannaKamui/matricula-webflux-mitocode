package com.miacademia.repository;

import com.miacademia.model.Estudiante;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

public interface EstudianteRepository extends GenericoRepository<Estudiante, String> {

}
