package com.arise.pharmacy.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JwtService {

    private final String SECRET_KEY = "f5g".repeat(20);

    public SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(String sub, Map<String,Object> claims){
        return Jwts.builder()
                .subject(sub)
                .claims(claims)
                .issuer("arise")
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

    public Date getExpiration(String token){
        return getClaims(token).getExpiration();
    }

    public boolean isValidToken(String sub, String token){
        return sub.equals(getSubject(token)) && getExpiration(token).after(new Date());
    }
}
