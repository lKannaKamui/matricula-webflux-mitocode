package com.miacademia.controller;

import com.miacademia.security.AuthRequest;
import com.miacademia.security.AuthResponse;
import com.miacademia.security.JwtUtil;
import com.miacademia.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

import java.util.Date;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final JwtUtil jwtUtil;
    private final UsuarioService service;

    @PostMapping("/login")
    public Mono<ResponseEntity<?>> login(@RequestBody AuthRequest authRequest){
        return service.searchByUser(authRequest.getUsuario())
                .map(userDetails -> {
                    if(BCrypt.checkpw(authRequest.getClave(), userDetails.getPassword())){
                        String token = jwtUtil.generateToken(userDetails);
                        Date expiration = jwtUtil.getExpirationDateFromToken(token);

                        return ResponseEntity.ok(new AuthResponse(token, expiration));
                    }else{
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                    }
                })
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}
