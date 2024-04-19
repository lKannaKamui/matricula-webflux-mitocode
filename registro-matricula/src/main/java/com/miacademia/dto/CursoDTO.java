package com.miacademia.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CursoDTO {

    private String id;

    @NotNull
    @Length(min = 5, max = 30)
    private String nombreCurso;

    @NotNull
    private String siglasCurso;

    @NotNull
    private Boolean estadoCurso;
}
