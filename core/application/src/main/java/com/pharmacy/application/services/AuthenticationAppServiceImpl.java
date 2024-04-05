package com.pharmacy.application.services;

import com.pharmacy.application.contracts.dtos.AuthenticationRequest;
import com.pharmacy.application.contracts.dtos.AuthenticationResponse;
import com.pharmacy.application.contracts.dtos.RegisterRequest;
import com.pharmacy.application.contracts.services.AuthenticationAppService;
import com.pharmacy.security.config.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.pharmacy.security.user.Role;
import com.pharmacy.security.user.User;
import com.pharmacy.security.user.UserRepositoryImpl;

@Service
public class AuthenticationAppServiceImpl implements AuthenticationAppService {

    private final UserRepositoryImpl userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    public AuthenticationAppServiceImpl(
            UserRepositoryImpl userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authManager
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authManager = authManager;
    }

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        User user = new User(
            request.getUsername(),
            passwordEncoder.encode(request.getPassword()),
            Role.USER
        );
        userRepository.save(user);
        String jwtToken = jwtService.generateTokenWithoutExtraClaims(user);
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
        return new AuthenticationResponse(jwtToken);
    }
}
