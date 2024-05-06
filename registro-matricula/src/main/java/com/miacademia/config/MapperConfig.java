package com.miacademia.config;

import com.miacademia.dto.CursoDTO;
import com.miacademia.dto.EstudianteDTO;
import com.miacademia.dto.MatriculaDTO;
import com.miacademia.model.Curso;
import com.miacademia.model.Estudiante;
import com.miacademia.model.Matricula;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class MapperConfig {

    @Bean("defaultMapper")
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean("cursoMapper")
    public ModelMapper cursoMapper(){
        ModelMapper cursoMapper = new ModelMapper();
        cursoMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        //lectura
        TypeMap<Curso, CursoDTO> lector = cursoMapper.createTypeMap(Curso.class, CursoDTO.class);
        lector.addMapping(Curso::getNombre, (destino, valor) -> destino.setNombre((String) valor));
        lector.addMapping(Curso::getSiglas, (destino, valor) -> destino.setSiglas((String) valor));
        lector.addMapping(Curso::getEstado, (destino, valor) -> destino.setEstado((Boolean) valor));

        //escritura
        TypeMap<CursoDTO,Curso> escritor = cursoMapper.createTypeMap(CursoDTO.class, Curso.class);
        escritor.addMapping(CursoDTO::getNombre, (destino, valor) -> destino.setNombre((String) valor));
        escritor.addMapping(CursoDTO::getSiglas, (destino, valor) -> destino.setSiglas((String) valor));
        escritor.addMapping(CursoDTO::getEstado, (destino, valor) -> destino.setEstado((Boolean) valor));
        return cursoMapper;
    }

    @Bean("estudianteMapper")
    public ModelMapper estudianteMapper(){
        ModelMapper estudianteMapper = new ModelMapper();
        estudianteMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        //lectura
        TypeMap<Estudiante, EstudianteDTO> lector = estudianteMapper.createTypeMap(Estudiante.class, EstudianteDTO.class);
        lector.addMapping(Estudiante::getNombres,(destino,valor) -> destino.setNombres((String) valor));
        lector.addMapping(Estudiante::getApellidos, (destino, valor) -> destino.setApellidos((String) valor));
        lector.addMapping(Estudiante::getDni, (destino, valor) -> destino.setDni((String) valor));
        lector.addMapping(Estudiante::getEdad, (destino, valor) -> destino.setEdad((Integer) valor));

        //escritura
        TypeMap<EstudianteDTO, Estudiante> escritor = estudianteMapper.createTypeMap(EstudianteDTO.class, Estudiante.class);
        escritor.addMapping(EstudianteDTO::getNombres, (destino, valor) -> destino.setNombres((String) valor));
        escritor.addMapping(EstudianteDTO::getApellidos, (destino, valor) -> destino.setApellidos((String) valor));
        escritor.addMapping(EstudianteDTO::getDni, (destino, valor) -> destino.setDni((String) valor));
        escritor.addMapping(EstudianteDTO::getEdad, (destino, valor) -> destino.setEdad((Integer) valor));

        return estudianteMapper;
    }

    @Bean("matriculaMapper")
    public ModelMapper matriculaMapper(){
        ModelMapper matriculaMapper = new ModelMapper();
        matriculaMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        //lectura
        TypeMap<Matricula, MatriculaDTO> lector = matriculaMapper.createTypeMap(Matricula.class, MatriculaDTO.class);
        lector.addMapping(Matricula::getFecha, (destino, valor) -> destino.setFecha((LocalDateTime) valor));
        lector.addMapping(e -> e.getEstudiante().getId(), (destino, valor) -> destino.getEstudiante().setId((String) valor));
        lector.addMapping(e -> e.getEstudiante().getNombres(),(destino, valor) -> destino.getEstudiante().setNombres((String) valor));
        lector.addMapping(e -> e.getEstudiante().getDni(), (destino, valor) -> destino.getEstudiante().setDni((String) valor));
        lector.addMapping(e -> e.getEstado(), (destino, valor) -> destino.setEstado((Boolean) valor));

        //escritura
        TypeMap<MatriculaDTO, Matricula> escritor = matriculaMapper.createTypeMap(MatriculaDTO.class, Matricula.class);
        escritor.addMapping(MatriculaDTO::getFecha, (destino, valor) -> destino.setFecha((LocalDateTime) valor));
        escritor.addMapping(e -> e.getEstudiante().getId(), (destino, valor) -> destino.getEstudiante().setId((String) valor));
        escritor.addMapping(e -> e.getEstudiante().getNombres(), (destino, valor) -> destino.getEstudiante().setNombres((String) valor));
        escritor.addMapping(e -> e.getEstudiante().getDni(), (destino, valor) -> destino.getEstudiante().setDni((String) valor));
        escritor.addMapping(e -> e.getEstado(), (destino, valor) -> destino.setEstado((Boolean) valor));

        return matriculaMapper;
    }
}