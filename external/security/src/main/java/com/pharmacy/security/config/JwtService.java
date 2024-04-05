package com.pharmacy.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.lang.Function;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    // 256-bit HEXDEC
    private static final String SECRETE_KEY = "D3saLo5vgxWtBEZ/J1/QIgIsFgCuRx0EMm9/zTxcTX4=";

    private SecretKey getPublicSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRETE_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateTokenWithExtraClaims(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts
            .builder()
            .claims()
            .add(extraClaims)
            .subject(userDetails.getUsername())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) // 24H
            .and()
            .signWith(getPublicSigningKey(), Jwts.SIG.HS256)  // GENERATE JWT WITH SECRET_KEY
            .compact();
    }

    public String generateTokenWithoutExtraClaims(UserDetails userDetails) {
        return generateTokenWithExtraClaims(new HashMap<>(), userDetails);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
            .verifyWith(getPublicSigningKey())  // VERIFY JWT WITH SECRET_KEY
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    public<T> T extractClaim(String token, Function<Claims, T> claimsREsolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsREsolver.apply(claims);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject); // ONLY 'SUBJECT' PROPERTY OF JWT
    }

    private Date extracExpiration(String token) {
        return extractClaim(token, Claims::getExpiration); // ONLY 'EXPIRATION' PROPERTY OF JWT
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extracExpiration(token).before(new Date());
    }
}
