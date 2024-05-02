package com.miacademia.model;

import com.miacademia.dto.EstudianteDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collation = "matriculas")
public class Matricula {

    @Id
    @EqualsAndHashCode.Include
    private String id;

    private LocalDateTime fecha;

    private Estudiante estudiante;

    private List<Curso> cursos;

    private Boolean estado;
}
