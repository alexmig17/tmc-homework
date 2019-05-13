package com.epam.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epam.test.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

}
