package org.garden.com.security.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@NoArgsConstructor
public class JwtAuthenticationResponse {

    private String token;

    public JwtAuthenticationResponse(String token) {
        this.token = token;
    }
}