package com.example.demo.util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtils {

    private final String SECRET_STRING = "qwertyuioplkjhgfdsazxcvbnm1029384756:-";

//    Convert the string into a Key object compatible with JJWT
    private final SecretKey SIGNING_KEY = Keys.hmacShaKeyFor(SECRET_STRING.getBytes());

//    Generate a token for a specific username
    public String generateToken(String username){
        long oneHourInMs = 1000*60*60;   // 1000ms * 60s * 60m
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+ oneHourInMs))
                .signWith(SIGNING_KEY)
                .compact();
    }

    // 1. This is the method your Filter is looking for
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // 2. This helper method is required by the one above
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // 3. This does the heavy lifting of parsing the token
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(SIGNING_KEY) // Use the SecretKey we defined
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }



}
