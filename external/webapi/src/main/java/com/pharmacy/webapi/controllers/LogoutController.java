package com.pharmacy.webapi.controllers;

import com.pharmacy.security.config.JwtService;
import com.pharmacy.security.user.Token;
import com.pharmacy.security.user.TokenRepositoryImpl;
import com.pharmacy.security.user.User;
import com.pharmacy.security.user.UserRepositoryImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import java.util.List;

// We must have "fat" controller and duplicated revokeAllUserTokens()
// because of circular dependency involved by SpringSecurityConfig design (if we define LogoutHandler in AuthController)
// AuthenticationService -> SecurityConfiguration -> AuthController(LogoutHandler) -> AuthenticationService(logout/revokeAllUserTokens)
@Component
public class LogoutController implements LogoutHandler {

    private final JwtService jwtService;
    private final UserRepositoryImpl userRepository;
    private final TokenRepositoryImpl tokenRepository;

    public LogoutController(
            JwtService jwtService,
            UserRepositoryImpl userRepository,
            TokenRepositoryImpl tokenRepository
    ) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        final String jwtAccessToken;
        final String username;
        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return;
        }
        jwtAccessToken = authorizationHeader.substring(7);
        username = jwtService.extractUsername(jwtAccessToken);
        if (username != null) {
            User user = this.userRepository.findByUsername(username)
                    .orElseThrow();
            if (jwtService.isTokenValid(jwtAccessToken, user)) {
                revokeAllUserTokens(user);
            }
        }
    }

    private void revokeAllUserTokens(User user) {
        List<Token> validTokens = tokenRepository.findAllValidTokensByUser(user.getId());
        if (validTokens.isEmpty())
            return;
        validTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validTokens);
    }
}
