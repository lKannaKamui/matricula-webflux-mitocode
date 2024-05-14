package com.miacademia.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotNull(message = "el campo nombre no puede ser nulo")
    private String nombre;

    @NotNull(message = "el campo siglas no puede ser nulo")
    private String siglas;

    @NotNull(message = "el campo estado no puede ser nulo")
    private Boolean estado;
}
