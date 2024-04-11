package org.garden.com.security;

import org.garden.com.security.model.JwtAuthenticationResponse;
import org.garden.com.security.model.SignInRequest;

public interface AuthenticationService {

    JwtAuthenticationResponse authenticate(SignInRequest request);
}