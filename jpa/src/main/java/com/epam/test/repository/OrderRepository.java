package com.epam.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epam.test.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

}
