package com.epam.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epam.test.entity.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {

}
