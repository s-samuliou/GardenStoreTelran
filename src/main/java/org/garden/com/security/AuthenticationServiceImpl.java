package org.garden.com.security;

import lombok.extern.slf4j.Slf4j;
import org.garden.com.security.model.JwtAuthenticationResponse;
import org.garden.com.security.model.SignInRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthenticationServiceImpl implements AuthenticationService{

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userService;

    @Autowired
    private JwtService jwtService;

    @Override
    public JwtAuthenticationResponse authenticate(SignInRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getLogin(),
                        request.getPassword()));
        UserDetails user = userService.loadUserByUsername(request.getLogin());

        String token = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(token);
    }
}