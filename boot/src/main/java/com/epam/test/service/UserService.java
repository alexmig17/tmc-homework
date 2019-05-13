package com.epam.test.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.epam.test.converter.UserConverter;
import com.epam.test.converter.UserRegistrationConverter;
import com.epam.test.dao.UserDao;
import com.epam.test.dto.UserDto;
import com.epam.test.dto.UserRegistrationFormDto;
import com.epam.test.entity.User;
import com.epam.test.exception.NotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

    private final UserDao userDao;
    private final UserRegistrationConverter userRegistrationConverter;
    private final UserConverter userConverter;

    public UserDto get(Integer id) {

        User user = userDao.get(id).orElseThrow(NotFoundException::new);
        return userConverter.toDto(user);
    }

    public List<UserDto> getAll() {
        List<User> users = userDao.getAll();
        return userConverter.toDtoList(users);
    }

    public Integer create(UserRegistrationFormDto formDto) {
        return userDao.update(userRegistrationConverter.toEntity(formDto));
    }

    public Integer update(UserDto userDto) {
        userDto.setRole(null);
        User user = userConverter.toEntity(userDto);
        return userDao.update(user);
    }

    public boolean delete(UserDto userDto) {
        User user = userConverter.toEntity(userDto);
        return userDao.delete(user);
    }
}
