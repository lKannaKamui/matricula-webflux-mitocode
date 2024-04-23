package com.miacademia.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EstudianteDTO {

    private String id;

    @NotNull
    private String nombres;

    @NotNull
    private String apellidos;

    @NotNull
    private String dni;

    @NotNull
    private Integer edad;
}
