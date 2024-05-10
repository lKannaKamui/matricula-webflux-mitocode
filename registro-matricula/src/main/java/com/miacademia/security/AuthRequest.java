package com.miacademia.security;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    private String usuario;
    private String clave;

}
