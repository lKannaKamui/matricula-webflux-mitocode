package com.miacademia.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil implements Serializable {
    //visita pag. sha512online para generar llave secreta para firmar tokens

    public final long JWT_TOKEN_VALIDITY = 5 * 60 * 1000; //5 minutos

    @Value("${jjwt.secret}")
    private String secret;

    public String generateToken(Usuario usuario) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", usuario.getRoles());
        claims.put("username", usuario.getUsername());
        claims.put("password-demo", "sample");

        return doGenerateToken(claims, usuario.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String username) {

        SecretKey key = Keys.hmacShaKeyFor(this.secret.getBytes());

        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(key)
                .compact();
    }

    public Claims getAllClaimsFromToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(this.secret.getBytes());

        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }

    public String getUsernameFromToken(String token){
        return getAllClaimsFromToken(token).getSubject();
    }

    public Date getExpirationDateFromToken(String token){
        return getAllClaimsFromToken(token).getExpiration();
    }

    private boolean isTokenExpired(String token){
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public boolean validateToken(String token){
        return !isTokenExpired(token);
    }
}
