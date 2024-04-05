package com.pharmacy.application.contracts.services;

import com.pharmacy.application.contracts.dtos.AuthenticationRequest;
import com.pharmacy.application.contracts.dtos.AuthenticationResponse;
import com.pharmacy.application.contracts.dtos.RegisterRequest;

public interface AuthenticationAppService {
    public AuthenticationResponse register (RegisterRequest request);
    public AuthenticationResponse authenticate(AuthenticationRequest request);
}
