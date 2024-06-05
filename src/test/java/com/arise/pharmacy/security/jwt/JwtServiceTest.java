package com.arise.pharmacy.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


class JwtServiceTest {

    JwtService underTest;

    @BeforeEach
    void setUp(){
        underTest = new JwtService();
    }

    @Test
    void generateToken() {

        //GIVEN
        String sub = "user@gmail.com";
        Map<String,Object> claims = Map.of("authority","");

        //WHEN
        String token = underTest.generateToken(sub,claims);

        //THEN
        Claims tokenClaims = Jwts.parser()
                .verifyWith(underTest.getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        assertThat(sub).isEqualTo(tokenClaims.getSubject());
        assertThat(claims.get("authority")).isEqualTo(tokenClaims.get("authority"));
        assertThat("arise").isEqualTo(tokenClaims.getIssuer());
        assertThat(java.sql.Date.valueOf(LocalDate.now().plusDays(3)))
                .isEqualTo(tokenClaims.getExpiration());
    }

    @Test
    void isValidTokenReturnsTrue(){

        //GIVEN
        String token = Jwts.builder()
                .subject("user@gmail.com")
                .issuedAt(new Date())
                .expiration(java.sql.Date.valueOf(LocalDate.now().plusDays(3)))
                .signWith(underTest.getSigningKey())
                .compact();

        //WHEN
        boolean isValid = underTest.isValidToken("user@gmail.com",token);

        //THEN
        assertThat(isValid).isTrue();
    }

    @Test
    void isValidTokenWithInvalidSubject(){

        //GIVEN
        String token = Jwts.builder()
                .subject("user@gmail.com")
                .issuedAt(new Date())
                .expiration(java.sql.Date.valueOf(LocalDate.now().plusDays(3)))
                .signWith(underTest.getSigningKey())
                .compact();

        //WHEN
        boolean isValid = underTest.isValidToken("use@gmail.com",token);

        //THEN
        assertThat(isValid).isFalse();
    }

}