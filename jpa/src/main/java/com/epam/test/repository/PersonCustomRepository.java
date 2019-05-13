package com.epam.test.repository;

import java.util.List;

import com.epam.test.entity.Person;

public interface PersonCustomRepository {

    /**
     * find persons with count of orders equal to <code>minOrders</> or greater
     *
     * @param minOrders minim orders
     * @return List of Person
     */
    List<Person> personsWithMinOrders(Integer minOrders);

}
