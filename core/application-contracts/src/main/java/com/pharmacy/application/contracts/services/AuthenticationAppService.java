package com.pharmacy.application.contracts.services;

import com.pharmacy.application.contracts.dtos.AuthenticationRequest;
import com.pharmacy.application.contracts.dtos.AuthenticationResponse;
import com.pharmacy.application.contracts.dtos.RegisterRequest;
import com.pharmacy.application.contracts.dtos.RegisterResponse;

public interface AuthenticationAppService {
    public RegisterResponse register (RegisterRequest request);
    public AuthenticationResponse authenticate(AuthenticationRequest request);
    public AuthenticationResponse refreshToken(String authorizationHeader);
}
