package com.pharmacy.application.services;

import com.pharmacy.application.contracts.dtos.AuthenticationRequest;
import com.pharmacy.application.contracts.dtos.AuthenticationResponse;
import com.pharmacy.application.contracts.dtos.RegisterRequest;
import com.pharmacy.application.contracts.dtos.RegisterResponse;
import com.pharmacy.application.contracts.services.AuthenticationAppService;
import com.pharmacy.security.config.JwtService;
import com.pharmacy.security.user.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationAppServiceImpl implements AuthenticationAppService {

    private final UserRepositoryImpl userRepository;
    private final TokenRepositoryImpl tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    public AuthenticationAppServiceImpl(
            UserRepositoryImpl userRepositoryImpl,
            TokenRepositoryImpl tokenRepositoryImpl,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authManager
    ) {
        this.userRepository = userRepositoryImpl;
        this.tokenRepository = tokenRepositoryImpl;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authManager = authManager;
    }

    @Override
    public RegisterResponse register(RegisterRequest request) {
        User user = new User(
            request.getUsername(),
            passwordEncoder.encode(request.getPassword()),
            request.getEmail(),
            request.getFirstname(),
            request.getLastname(),
            Role.USER
        );
        User u = userRepository.save(user);
        return new RegisterResponse(u.getUsername(),u.getPassword(),u.getEmail(), u.getFirstname(), u.getLastname());
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Authentication authentication = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
            )
        );
        if (authentication.isAuthenticated()) {
            User user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow();
            String jwtAccessToken = jwtService.generateAccessTokenWithoutExtraClaims(user);
            String jwtRefreshToken = jwtService.generateRefreshTokenWithoutExtraClaims(user);
            revokeAllUserTokens(user);
            saveUserToken(user, TokenType.BEARER_ACCESS, jwtAccessToken);
            saveUserToken(user, TokenType.BEARER_REFRESH, jwtRefreshToken);
            return new AuthenticationResponse(jwtAccessToken, jwtRefreshToken);
        }
        return null;
    }

    @Override
    public AuthenticationResponse refreshToken(String authotizationHeader) {
        final String jwtRefreshToken;
        final String username;
        if(authotizationHeader == null || !authotizationHeader.startsWith("Bearer ")) {
            return null;
        }
        jwtRefreshToken = authotizationHeader.substring(7);
        username = jwtService.extractUsername(jwtRefreshToken);
        if (username != null) {
            User user = this.userRepository.findByUsername(username)
                    .orElseThrow();
            if (jwtService.isTokenValid(jwtRefreshToken, user)) {
                String jwtAccessToken = jwtService.generateAccessTokenWithoutExtraClaims(user);
                // once the refresh token is used, we should provide new one
                String newJwtRefreshToken = jwtService.generateRefreshTokenWithoutExtraClaims(user);
                revokeAllUserTokens(user);
                saveUserToken(user, TokenType.BEARER_ACCESS, jwtAccessToken);
                saveUserToken(user, TokenType.BEARER_REFRESH, newJwtRefreshToken);
                AuthenticationResponse authResponse = new AuthenticationResponse(jwtAccessToken, newJwtRefreshToken);
                return authResponse;
            }
        }
        return null;
    }

    private void saveUserToken(User user, TokenType tokenType, String jwtToken) {
        Token token = new Token(jwtToken, tokenType, false, false, user);
        tokenRepository.save(token);
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
