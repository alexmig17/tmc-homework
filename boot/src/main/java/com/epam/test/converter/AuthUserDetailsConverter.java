package com.epam.test.converter;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
public class AuthUserDetailsConverter {

    public User toUserDetails(com.epam.test.entity.User user) {
        Set<GrantedAuthority> roles = Stream.of(user.getRole()).map(this::newGrant).collect(Collectors.toSet());
        User userDetails = new User(user.getEmail(), user.getPassword(), roles);
        return userDetails;
    }

    private SimpleGrantedAuthority newGrant(String role) {
        return new SimpleGrantedAuthority(role);
    }
}
