package com.miacademia.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "cursos")
public class Curso {

    @Id
    @EqualsAndHashCode.Include
    private String id;

    @Field
    private String nombre;

    @Field
    private String siglas;

    @Field
    private Boolean estado;

}
