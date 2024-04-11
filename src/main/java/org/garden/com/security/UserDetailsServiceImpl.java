package org.garden.com.security;

import org.garden.com.entity.User;
import org.garden.com.exceptions.UserNotFoundException;
import org.garden.com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            org.garden.com.entity.User userEntity = userService.getByLogin(username);
            return new org.springframework.security.core.userdetails.User(userEntity.getName(), userEntity.getPassword(),
                    List.of(new SimpleGrantedAuthority(userEntity.getRole().getRoleName())));
        } catch (UserNotFoundException exception) {
            throw new UsernameNotFoundException(exception.getMessage());
        }
    }
}