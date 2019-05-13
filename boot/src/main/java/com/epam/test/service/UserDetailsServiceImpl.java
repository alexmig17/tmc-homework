package com.epam.test.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.epam.test.converter.AuthUserDetailsConverter;
import com.epam.test.dao.UserDao;
import com.epam.test.entity.User;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDao userDao;
    private final AuthUserDetailsConverter authUserDetailsConverter;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDao.getByEmail(email).orElseThrow(RuntimeException::new);
        return authUserDetailsConverter.toUserDetails(user);
    }
}
