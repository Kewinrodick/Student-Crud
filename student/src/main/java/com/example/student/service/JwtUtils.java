package com.example.student.service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;


@Service
public class JwtUtils{
    @Value("${security.jwt.secret-key}")
    private String jwtSecret;
    @Value("${security.jwt.expiration-time}")
    private int jwtExpireIn;

    public String generateJwtToken(UserDetails userDetails) {



        String token  = Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("authorities", userDetails.getAuthorities())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+jwtExpireIn))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
        System.out.println("in /jwtUtils/generateToken");
        return token;
    }

    public boolean validateJwtToken(String token)throws JwtException
    {
        try
        {
            Jwts.parser()
                    .verifyWith(getSignKey())  // Use the signing key derived from jwtSecret
                    .build()
                    .parseSignedClaims(token) // This will throw an exception if the token is invalid
            .getPayload();
            return true;
        }
        catch (JwtException | IllegalArgumentException e)
        {        return false;
        }

    }

    public SecretKey getSignKey(){
        byte[] keyBytes = jwtSecret.getBytes();
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);
        return key;
    }
    public String getUsernameFromJwtToken(String token)
    {
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
