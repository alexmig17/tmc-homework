package com.epam.test.repository;

import java.util.Optional;
import javax.persistence.LockModeType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.epam.test.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Page<Product> findByNameContaining(@Param("name") String name, Pageable pageRequest);

    @Override
    @Lock(LockModeType.PESSIMISTIC_FORCE_INCREMENT)
    Optional<Product> findById(Integer integer);
}

