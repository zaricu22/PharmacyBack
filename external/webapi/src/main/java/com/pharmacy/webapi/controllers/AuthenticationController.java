package com.pharmacy.webapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pharmacy.application.contracts.dtos.AuthenticationRequest;
import com.pharmacy.application.contracts.dtos.AuthenticationResponse;
import com.pharmacy.application.contracts.dtos.RegisterRequest;
import com.pharmacy.application.services.AuthenticationAppServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
public class AuthenticationController {

    private final AuthenticationAppServiceImpl authAppService;

    public AuthenticationController(AuthenticationAppServiceImpl authAppService) {
        this.authAppService = authAppService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authAppService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authAppService.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        AuthenticationResponse authResponse = authAppService.refreshToken(authorizationHeader);
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
    }

}
