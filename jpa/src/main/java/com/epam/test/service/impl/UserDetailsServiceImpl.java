package com.epam.test.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.epam.test.converter.AuthUserDetailsConverter;
import com.epam.test.entity.User;
import com.epam.test.exception.NotFoundException;
import com.epam.test.repository.PersonRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PersonRepository userRepository;
    private final AuthUserDetailsConverter authUserDetailsConverter;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.getByEmail(email).orElseThrow(NotFoundException::new);

        return authUserDetailsConverter.toUserDetails(user);
    }
}
