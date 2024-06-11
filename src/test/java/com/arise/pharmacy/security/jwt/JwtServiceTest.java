package com.arise.pharmacy.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JwtServiceTest {

    @Autowired
    JwtService underTest;

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
                .issuer("arise")
                .issuedAt(new Date())
                .expiration(java.sql.Date.valueOf(LocalDate.now().plusDays(3)))
                .signWith(underTest.getSigningKey())
                .compact();

        //WHEN
        boolean isValid = underTest.isValidToken(token);

        //THEN
        assertThat(isValid).isTrue();
    }

    @Test
    void isValidTokenWithInvalidIssuer(){

        //GIVEN
        String token = Jwts.builder()
                .subject("user@gmail.com")
                .issuer("issuer")
                .issuedAt(new Date())
                .expiration(java.sql.Date.valueOf(LocalDate.now().plusDays(3)))
                .signWith(underTest.getSigningKey())
                .compact();

        //WHEN
        boolean isValid = underTest.isValidToken(token);

        //THEN
        assertThat(isValid).isFalse();
    }

    @Test
    void isValidTokenWithNoSubject(){

        //GIVEN
        String token = Jwts.builder()
                .issuer("arise")
                .issuedAt(new Date())
                .expiration(java.sql.Date.valueOf(LocalDate.now().plusDays(3)))
                .signWith(underTest.getSigningKey())
                .compact();

        //WHEN
        boolean isValid = underTest.isValidToken(token);

        //THEN
        assertThat(isValid).isFalse();
    }

}