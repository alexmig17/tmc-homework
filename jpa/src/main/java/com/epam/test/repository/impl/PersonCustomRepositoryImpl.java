package com.epam.test.repository.impl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.epam.test.entity.Person;
import com.epam.test.entity.Person_;
import com.epam.test.repository.PersonCustomRepository;

@Repository
public class PersonCustomRepositoryImpl implements PersonCustomRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Person> personsWithMinOrders(Integer minOrders) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Person> criteriaQuery = cb.createQuery(Person.class);
        Root<Person> root = criteriaQuery.from(Person.class);
        Predicate predicate = cb.ge(cb.size(root.get(Person_.ORDERS)), minOrders);
        criteriaQuery.select(root).where(predicate);
        TypedQuery<Person> query = em.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
