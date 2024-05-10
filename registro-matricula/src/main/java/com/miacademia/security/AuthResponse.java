package com.miacademia.security;

import java.util.Date;

public record AuthResponse(
        String token,
        Date fechaExpiracion
) {
}
