package com.pharmacy.application.services;

import com.pharmacy.application.contracts.dtos.AuthenticationRequest;
import com.pharmacy.application.contracts.dtos.AuthenticationResponse;
import com.pharmacy.application.contracts.dtos.RegisterRequest;
import com.pharmacy.application.contracts.services.AuthenticationAppService;
import com.pharmacy.security.config.JwtService;
import com.pharmacy.security.user.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    public AuthenticationResponse register(RegisterRequest request) {
        User user = new User(
            request.getUsername(),
            passwordEncoder.encode(request.getPassword()),
            request.getEmail(),
            request.getFirstname(),
            request.getLastname(),
            Role.USER
        );
        User savedUser = userRepository.save(user);
        String jwtToken = jwtService.generateTokenWithoutExtraClaims(user);
        saveUserToken(savedUser, jwtToken);
        return new AuthenticationResponse(jwtToken);
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
            )
        );
        User user = userRepository.findByUsername(request.getUsername())
             .orElseThrow();
        String jwtToken = jwtService.generateTokenWithoutExtraClaims(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return new AuthenticationResponse(jwtToken);
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

    private void saveUserToken(User user, String jwtToken) {
        Token token = new Token(jwtToken, TokenType.BEARER, false, false, user);
        tokenRepository.save(token);
    }


}
