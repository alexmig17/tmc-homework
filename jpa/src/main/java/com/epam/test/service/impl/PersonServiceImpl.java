package com.epam.test.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.epam.test.converter.PersonConverter;
import com.epam.test.converter.PersonRegistrationConverter;
import com.epam.test.dto.PersonDto;
import com.epam.test.dto.UserRegistrationDto;
import com.epam.test.entity.Person;
import com.epam.test.exception.NotFoundException;
import com.epam.test.exception.PersonExistException;
import com.epam.test.exception.UnauthenticatedException;
import com.epam.test.repository.PersonRepository;
import com.epam.test.service.PersonService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final PersonConverter personConverter;
    private final PersonRegistrationConverter personRegistrationConverter;

    @Override
    public Integer create(UserRegistrationDto formDto) {
        if (personRepository.countUsersWithEmail(formDto.getEmail()) > 0) {
            throw new PersonExistException(formDto.getEmail());
        }
        Person person = personRegistrationConverter.toEntity(formDto);
        return personRepository.save(person).getId();
    }

    @Override
    public List<PersonDto> findPersonsWithBasket() {
        return personConverter.toDtoList(personRepository.findPersonsWithBasket());
    }

    @Override
    public List<PersonDto> findPersonsRegisteredOnDay(LocalDate localDate) {
        return personConverter.toDtoList(personRepository.findPersonsRegisteredOnDay(localDate));
    }

    @Override
    public List<PersonDto> findPersonWithMiOrders(Integer orderCount) {
        return personConverter.toDtoList(personRepository.personsWithMinOrders(orderCount));
    }

    @Override
    public Person getPersonFormAuthContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person person;
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            person = personRepository.getByEmail(currentUserName).orElseThrow(NotFoundException::new);
        } else {
            throw new UnauthenticatedException();
        }
        return person;
    }

    @Override
    public PersonDto update(PersonDto userDto) {
        if (userDto.getId() == null) {
            throw new UnsupportedOperationException("unsupported operation for new user, please use create method instead");
        }
        Person person = personConverter.toEntity(userDto);
        Person existedUser = personRepository.findById(person.getId()).orElseThrow(NotFoundException::new);
        person.setRole(existedUser.getRole());
        person.setPassword(existedUser.getPassword());
        return personConverter.toDto(personRepository.save(person));
    }

    @Override
    public void delete(Integer id) {
        personRepository.deleteById(id);
    }

    @Override
    public PersonDto get(Integer id) {
        Person person = personRepository.findById(id).orElseThrow(NotFoundException::new);
        return personConverter.toDto(person);
    }

    @Override
    public Page<PersonDto> getAll(Pageable pageable) {
        return personRepository.findAll(pageable).map(personConverter::toDto);
    }
}
