package com.epam.test.service;

import java.time.LocalDate;
import java.util.List;

import com.epam.test.dto.PersonDto;
import com.epam.test.dto.UserRegistrationDto;
import com.epam.test.entity.Person;

public interface PersonService extends CrudService<PersonDto, Integer> {

    Integer create(UserRegistrationDto formDto);

    List<PersonDto> findPersonsWithBasket();

    List<PersonDto> findPersonsRegisteredOnDay(LocalDate localDate);

    List<PersonDto> findPersonWithMiOrders(Integer orderCount);

    Person getPersonFormAuthContext();
}
