package org.garden.com.enums;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
@RequiredArgsConstructor
public enum Role implements GrantedAuthority {

    ADMIN("ADMIN"),
   CUSTOMER("CUSTOMER");

    private final String value;


    @Override
    public String getAuthority() {
        return value;
    }
}
