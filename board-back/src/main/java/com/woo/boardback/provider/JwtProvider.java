package com.woo.boardback.provider;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class JwtProvider {

    @Value("${secret-key}")
    private String secretKey;

    // JWT 생성
    public String create(String email) {
        Date expiredDate = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        String jwt = Jwts.builder()
            .signWith(key, SignatureAlgorithm.HS256)
            .setSubject(email)
            .setIssuedAt(new Date())
            .setExpiration(expiredDate)
            .compact();

        log.info("JWT CREATED!");

        return jwt;
    }

    public String validate(String jwt) {
        Claims claims = null;
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt);
            claims = claimsJws.getBody();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        
        String subject = claims.getSubject();
        log.info("VALIDATED!");

        return subject;
    }
}


/*
 * package com.woo.boardback.provider;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

    @Value("${secret-key}")
    private String secretKey;

    @SuppressWarnings("deprecation")
    public String create(String email) {
    
        Date expiredDate = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));

       
        String jwt = Jwts.builder()
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .setSubject(email).setIssuedAt(new Date()).setExpiration(expiredDate)
            .compact();
       
        return jwt;
    }

    @SuppressWarnings("deprecation")
    public String validate(String jwt) {
        Claims claims = null;

        try {
            claims = Jwts.parser().setSigningKey(secretKey)
            .parseClaimsJws(jwt).getBody();
    
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return claims.getSubject();
    } 
}

 */