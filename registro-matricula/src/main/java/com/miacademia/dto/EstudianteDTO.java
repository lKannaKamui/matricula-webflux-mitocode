package com.miacademia.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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

    @NotNull(message = "el campo nombres no puede ser nulo")
    private String nombres;

    @NotNull(message = "el campo apellidos no puede ser nulo")
    private String apellidos;

    @NotNull(message = "el campo DNI no puede ser nulo")
    @Min(message = "El campo DNI lleva 8 digitos", value = 8)
    private String dni;

    @NotNull(message = "el campo edad no puede ser nulo")
    @Min(message = "La edad minima es 16", value = 16)
    private Integer edad;
}
