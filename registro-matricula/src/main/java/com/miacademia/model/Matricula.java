package com.miacademia.model;

import com.miacademia.dto.EstudianteDTO;
import jakarta.validation.constraints.Future;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "matriculas")
public class Matricula {

    @Id
    @EqualsAndHashCode.Include
    private String id;

    @Field
    private LocalDateTime fecha;

    @Field
    private Estudiante estudiante;

    @Field
    private List<Curso> cursos;

    @Field
    private Boolean estado;
}
