package com.pharmacy.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.lang.Function;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    // 256-bit HEXDEC
    @Value("${application.security.jwt.secrete-key}")
    private String secreteKey;
    @Value("${application.security.jwt.access-token-expiration}")
    private long jwtAccessExpiration;
    @Value("${application.security.jwt.refresh-token-expiration}")
    private long jwtRefreshExpiration;

    private SecretKey getPublicSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secreteKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessTokenWithoutExtraClaims(
            UserDetails userDetails
    ) {
        return buildToken(new HashMap<>(), userDetails, jwtAccessExpiration);
    }

    public String generateRefreshTokenWithoutExtraClaims(
            UserDetails userDetails
    ) {
        return buildToken(new HashMap<>(), userDetails, jwtRefreshExpiration);
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        return Jwts
                .builder()
                .claims()
                .add(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .and()
                .signWith(getPublicSigningKey(), Jwts.SIG.HS256)  // GENERATE JWT WITH SECRET_KEY
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject); // ONLY 'SUBJECT' PROPERTY OF JWT
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration); // ONLY 'EXPIRATION' PROPERTY OF JWT
    }

    public<T> T extractClaim(String token, Function<Claims, T> claimsREsolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsREsolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {    // throws ExpiredJwtException
        return Jwts.parser()
                .verifyWith(getPublicSigningKey())  // VERIFY JWT WITH SECRET_KEY
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
