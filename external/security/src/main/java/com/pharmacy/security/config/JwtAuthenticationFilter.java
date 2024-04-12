package com.pharmacy.security.config;

import com.pharmacy.security.user.Token;
import com.pharmacy.security.user.TokenRepositoryImpl;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenRepositoryImpl tokenRepository;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            UserDetailsService userDetailsService,
            TokenRepositoryImpl tokenRepository
        ) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.tokenRepository = tokenRepository;
    }

    // Executed on each request which should be Authenticated
    // It adds JWT tokens just after first non-permitted request (not during [/authentication,/register].permitAll())
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String jwtToken;
        String username = null;

        // If request doesn't contain JWT token, just escape checks
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwtToken = authHeader.substring(7);
        final Token tokenInDB = tokenRepository.findByToken(jwtToken)
                .orElse(null);
        try {
            username = jwtService.extractUsername(jwtToken); // throws ExpiredJwtException
        } catch (ExpiredJwtException expiredJwtException) {
            if (tokenInDB != null && !tokenInDB.isExpired()) {
                logger.warn(tokenInDB.getTokenType()+" has expired!");
                tokenInDB.setExpired(true);
                tokenInDB.setRevoked(true);
                tokenRepository.save(tokenInDB);
            }
        }
        // SecurityContextHolder store Authentication only for current HTTP request
        // It is cleared immediately after request processing completed
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // class User implements UserDetails
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            boolean isTokenValidInRequest = jwtService.isTokenValid(jwtToken, userDetails);
            boolean isTokenValidInDB = tokenInDB != null && !tokenInDB.isExpired() && !tokenInDB.isRevoked();
            if (isTokenValidInRequest && isTokenValidInDB) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,   // we don't have credentials
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                // Add AccessToken and RefreshToken to SecurityContextHolder
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
