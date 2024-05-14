package com.miacademia.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    @JsonProperty(value = "username")
    private String usuario;

    @JsonProperty(value = "password")
    private String clave;

}
