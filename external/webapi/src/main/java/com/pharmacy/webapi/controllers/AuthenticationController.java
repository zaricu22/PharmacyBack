package com.pharmacy.webapi.controllers;

import com.pharmacy.application.contracts.dtos.AuthenticationRequest;
import com.pharmacy.application.contracts.dtos.AuthenticationResponse;
import com.pharmacy.application.contracts.dtos.RegisterRequest;
import com.pharmacy.application.services.AuthenticationAppServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
