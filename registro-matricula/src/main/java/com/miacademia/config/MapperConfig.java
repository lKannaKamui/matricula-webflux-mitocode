package com.miacademia.config;

import com.miacademia.dto.CursoDTO;
import com.miacademia.model.Curso;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;

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
}