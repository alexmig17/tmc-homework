package com.epam.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epam.test.entity.Basket;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Integer> {

}
