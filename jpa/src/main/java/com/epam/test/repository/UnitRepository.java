package com.epam.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epam.test.entity.Unit;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Integer> {

}
