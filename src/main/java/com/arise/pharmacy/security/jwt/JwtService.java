package com.arise.pharmacy.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JwtService {

    @Value("${spring.jwt.secret}")
    private String SECRET_KEY;

    @Getter
    @Value("${spring.jwt.issuer}")
    private String ISSUER;

    public SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }


    public String generateToken(String sub, Map<String,Object> claims){
        return Jwts.builder()
                .subject(sub)
                .claims(claims)
                .issuer(ISSUER)
                .issuedAt(new Date())
                .expiration(java.sql.Date.valueOf(LocalDate.now().plusDays(3)))
                .signWith(getSigningKey())
                .compact();
    }

    public String generateToken(String sub, List<String> authorities){
        return generateToken(sub,Map.of("authorities",authorities));
    }

    private Claims getClaims(String token){
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getSubject(String token){
        return getClaims(token).getSubject();
    }

    private Date getExpiration(String token){
        return getClaims(token).getExpiration();
    }

    private String getIssuer(String token) { return getClaims(token).getIssuer(); }

    public boolean isValidToken(String token){
        return getIssuer(token).equals(ISSUER) && getExpiration(token).after(new Date())
                && getSubject(token) != null;
    }
}
